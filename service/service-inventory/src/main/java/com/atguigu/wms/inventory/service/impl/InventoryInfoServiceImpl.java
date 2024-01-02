package com.atguigu.wms.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.base.client.WareConfigFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.common.constant.MqConst;
import com.atguigu.wms.common.constant.RedisConst;
import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.service.RabbitService;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.enums.OutOrderStatus;
import com.atguigu.wms.inventory.service.InvBusinessRepeatService;
import com.atguigu.wms.inventory.service.InvLogService;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.base.WareConfig;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.model.outbound.OutPickingItem;
import com.atguigu.wms.outbound.client.OutboundFeignClient;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.vo.inventory.InventoryFormVo;
import com.atguigu.wms.vo.outbound.OrderItemLockVo;
import com.atguigu.wms.inventory.mapper.InventoryInfoMapper;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import com.atguigu.wms.vo.outbound.OrderResponseVo;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InventoryInfoServiceImpl extends ServiceImpl<InventoryInfoMapper, InventoryInfo> implements InventoryInfoService {

	@Resource
	private InventoryInfoMapper inventoryInfoMapper;

	@Resource
	private InvLogService invLogService;

	@Resource
	private InvBusinessRepeatService invBusinessRepeatService;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private StorehouseInfoFeignClient storehouseInfoFeignClient;

	@Resource
	private GoodsInfoFeignClient goodsInfoFeignClient;

	@Resource
	private OutboundFeignClient outboundFeignClient;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Resource
	private RabbitService rabbitService;

	@Resource
	private WareConfigFeignClient wareConfigFeignClient;

	@Override
	public PageVo<GoodsInfo> selectPage(Page<GoodsInfo> pageParam, GoodsInfoQueryVo goodsInfoQueryVo) {
		PageVo<GoodsInfo> pageVo = goodsInfoFeignClient.findPage(pageParam.getCurrent(), pageParam.getSize(), goodsInfoQueryVo);
		List<GoodsInfo> goodsInfoList = pageVo.getRecords();
		if(CollectionUtils.isEmpty(goodsInfoList)) return pageVo;

		List<Long> goodsIdList = goodsInfoList.stream().map(GoodsInfo::getId).collect(Collectors.toList());

		LambdaQueryWrapper<InventoryInfo> queryWrapper = new LambdaQueryWrapper<InventoryInfo>();
		queryWrapper.in(InventoryInfo::getGoodsId, goodsIdList);
		if(null != AuthContextHolder.getWarehouseId()) {
			queryWrapper.eq(InventoryInfo::getWarehouseId, AuthContextHolder.getWarehouseId());
		}
		List<InventoryInfo> inventoryInfoList = this.list(queryWrapper);
		inventoryInfoList.forEach(item -> {
			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getStorehouseId()));
		});
		Map<Long, List<InventoryInfo>> goodsIdToInventoryInfoListMap = inventoryInfoList.stream().collect(Collectors.groupingBy(InventoryInfo::getGoodsId));
		for(GoodsInfo goodsInfo : goodsInfoList) {
			List<InventoryInfo> inventoryInfos = goodsIdToInventoryInfoListMap.get(goodsInfo.getId());
			if(CollectionUtils.isEmpty(inventoryInfos)) inventoryInfos = new ArrayList<>();
			goodsInfo.setInventoryInfoList(inventoryInfos);
		}
		return pageVo;
	}

	@Transactional(rollbackFor = {Exception.class})
    @Override
    public Boolean syncInventory(String inOrderNo, List<InventoryInfoVo> inventoryInfoVoList, InvLogType invLogType) {
		for(InventoryInfoVo inventoryInfoVo : inventoryInfoVoList) {
			InventoryInfo inventoryInfo = this.inventoryInfoMapper.getCheck(inventoryInfoVo.getWarehouseId(), inventoryInfoVo.getGoodsId());
			if(null == inventoryInfo) {
				inventoryInfo = new InventoryInfo();
				BeanUtils.copyProperties(inventoryInfoVo, inventoryInfo);
				inventoryInfo.setTotalCount(inventoryInfoVo.getPutawayCount());
				inventoryInfo.setAvailableCount(inventoryInfoVo.getPutawayCount());
				inventoryInfo.setLockCount(0);
				inventoryInfo.setWarningCount(10);
				this.save(inventoryInfo);
			} else {
				inventoryInfoMapper.putaway(inventoryInfoVo.getWarehouseId(), inventoryInfoVo.getGoodsId(), inventoryInfoVo.getPutawayCount());
			}

			//记录日志
			invLogService.log(inventoryInfo.getWarehouseId(), inventoryInfo.getGoodsId(), invLogType, inventoryInfoVo.getPutawayCount(), "上架入库单号：" + inOrderNo);
		}
        return true;
    }

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public Boolean countingSyncInventory(String taskNo, List<InventoryInfoVo> inventoryInfoVoList, InvLogType invLogType) {
		for(InventoryInfoVo inventoryInfoVo : inventoryInfoVoList) {
			InventoryInfo inventoryInfo = this.inventoryInfoMapper.getCheck(inventoryInfoVo.getWarehouseId(), inventoryInfoVo.getGoodsId());
			if(null != inventoryInfo) {
				inventoryInfoMapper.putaway(inventoryInfoVo.getWarehouseId(), inventoryInfoVo.getGoodsId(), inventoryInfoVo.getPutawayCount());
				//记录日志
				invLogService.log(inventoryInfo.getWarehouseId(), inventoryInfo.getGoodsId(), invLogType, inventoryInfoVo.getPutawayCount(), "库内盘点任务单号：" + taskNo);
			} else {
				throw new WmsException(ResultCodeEnum.DATA_ERROR);
			}
		}
		return true;
	}

	@Override
	public InventoryInfo getByGoodsIdAndWarehouseId(Long goodsId, Long warehouseId) {
		return this.getOne(new LambdaQueryWrapper<InventoryInfo>().eq(InventoryInfo::getGoodsId, goodsId).eq(InventoryInfo::getWarehouseId,warehouseId));
	}

	@Override
	public List<InventoryInfo> findByStorehouseId(Long storehouseId) {
		List<InventoryInfo> list = this.list(new LambdaQueryWrapper<InventoryInfo>().eq(InventoryInfo::getStorehouseId, storehouseId));
		list.forEach(item -> {
			GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfo(item.getGoodsId());
			item.setGoodsInfo(goodsInfo);
			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getStorehouseId()));
		});
		return list;
	}

	/**
	 *
	 * @param orderLockVo
	 * @return 返回拆单后，仓库id对应分配的货品id列表
	 */
	public Result<List<OrderResponseVo>> checkAndLock(OrderLockVo orderLockVo) {
		String key = "checkAndLock:"+orderLockVo.getOrderNo();
		String keyList = "checkAndLockList:"+orderLockVo.getOrderNo();
		//防止重复请求
		boolean isExist = redisTemplate.opsForValue().setIfAbsent(key, orderLockVo.getOrderNo(), 1, TimeUnit.HOURS);
		if (!isExist) {
			String data = redisTemplate.opsForValue().get(keyList);
			if(!StringUtils.isEmpty(data)) {
				//返回计算结果
				List<OrderResponseVo> orderResponseVoList = JSONArray.parseArray(data, OrderResponseVo.class);
				return Result.ok(orderResponseVoList);
			} else {
				//还未计算出结果就再次提交
				return Result.build(null, ResultCodeEnum.LOCK_REPEAT);
			}
		}
		//获取购物项货品
		List<OrderItemLockVo> orderItemLockVoList = orderLockVo.getOrderItemVoList();
		//选出各个货品有库存的记录
		//货品id对应可以使用的仓库id列表
		Map<Long, List<Long>> goodsIdToAvailableWarehouseIdListMap = new HashMap<>();
		//验库存：满足条件的全部仓库
		List<Long> allWarehouseIdList = new ArrayList<>();
		Map<Long, Long> goodsIdToSkuIdMap = new HashMap<>();
		for(OrderItemLockVo orderItemLockVo : orderItemLockVoList) {
			//根据skuid获取货品id
			GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfoBySkuId(orderItemLockVo.getSkuId());
			if(null == goodsInfo) {
				redisTemplate.delete(key);
				return Result.build(null, ResultCodeEnum.INVENTORY_LESS);
			}
			orderItemLockVo.setGoodsId(goodsInfo.getId());
			goodsIdToSkuIdMap.put(goodsInfo.getId(), goodsInfo.getSkuId());

			// 验库存：查询，返回的是满足要求的库存列表
			List<InventoryInfo> availableInventoryInfoList = inventoryInfoMapper.check(orderItemLockVo.getGoodsId(), orderItemLockVo.getCount());
			if(CollectionUtils.isEmpty(availableInventoryInfoList)) {
				//没有库存
				redisTemplate.delete(key);
				return Result.build(null, ResultCodeEnum.INVENTORY_LESS);
			}
			List<Long> warehouseIdList = availableInventoryInfoList.stream().map(InventoryInfo::getWarehouseId).collect(Collectors.toList());
			goodsIdToAvailableWarehouseIdListMap.put(orderItemLockVo.getGoodsId(), warehouseIdList);
			allWarehouseIdList.addAll(warehouseIdList);
		}

		//去重,满足条件的全部仓库
		allWarehouseIdList = allWarehouseIdList.stream().distinct().collect(Collectors.toList());
		//根据用户地址给满足条件的仓库指定优先级
		OutOrderAddressVo outOrderAddressVo = new OutOrderAddressVo();
		BeanUtils.copyProperties(orderLockVo, outOrderAddressVo);
		outOrderAddressVo.setWarehouseIdList(allWarehouseIdList);
		//根据地址信息确定仓库优先级，排除不可用仓库（如其他城市的区域仓库），优先级越高越在前面
		List<Long> priorityWarehouseIdList = warehouseInfoFeignClient.findPriorityWarehouseIdList(outOrderAddressVo);
		if(CollectionUtils.isEmpty(priorityWarehouseIdList)) {
			//没有库存
			redisTemplate.delete(key);
			return Result.build(null, ResultCodeEnum.INVENTORY_LESS);
		}

		//根据仓库优先级给仓库分配货品
		List<OrderResponseVo> orderResponseVoList = new ArrayList<>();
		//去重处理，记录已经分配了仓库的货品
		List<Long> assignGoodsIdList = new ArrayList<>();
		for(Long warehouseId : priorityWarehouseIdList) {
			List<Long> goodsIdList = new ArrayList<>();
			List<Long> skuIdList = new ArrayList<>();
			Iterator<Map.Entry<Long, List<Long>>> iterator = goodsIdToAvailableWarehouseIdListMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Long, List<Long>> entry = iterator.next();
				Long goodsId = entry.getKey();
				if(!assignGoodsIdList.contains(goodsId)) {
					List<Long> warehouseIdList = entry.getValue();
					if(warehouseIdList.contains(warehouseId)) {
						goodsIdList.add(goodsId);
						skuIdList.add(goodsIdToSkuIdMap.get(goodsId));
						assignGoodsIdList.add(goodsId);
					}
				}
			}
			if(goodsIdList.size() > 0) {
				OrderResponseVo orderResponseVo = new OrderResponseVo();
				orderResponseVo.setWarehouseId(warehouseId);
				orderResponseVo.setSkuIdList(skuIdList);
				orderResponseVo.setGoodsIdList(goodsIdList);
				orderResponseVoList.add(orderResponseVo);
			}
		}
		//当前已经根据仓库优先级计算出了哪个仓库扣减哪些货品的库存，数据对象：orderResponseVoList
		// 锁库存：更新
		for(OrderResponseVo orderResponseVo: orderResponseVoList) {
			Long warehouseId = orderResponseVo.getWarehouseId();
			List<Long> goodsIdList = orderResponseVo.getGoodsIdList();
			List<OrderItemLockVo> currentGoodsLockVoList = orderItemLockVoList.stream().filter(orderItemLockVo -> goodsIdList.contains(orderItemLockVo.getGoodsId())).collect(Collectors.toList());
			// 锁库存：更新
			this.lock(orderLockVo.getOrderNo(), warehouseId, currentGoodsLockVoList);
		}
		// 只要有一个商品锁定失败，所有锁定成功的商品要解锁库存
		if (orderItemLockVoList.stream().anyMatch(orderItemLockVo -> !orderItemLockVo.getLock())) {
			// 获取所有锁定成功的商品，遍历解锁库存
			orderItemLockVoList.stream().filter(OrderItemLockVo::getLock).forEach(itemLockVo -> {
				this.inventoryInfoMapper.unLock(itemLockVo.getWarehouseId(), itemLockVo.getGoodsId(), itemLockVo.getCount());

				//记录日志
				invLogService.log(itemLockVo.getWarehouseId(), itemLockVo.getGoodsId(), InvLogType.OUT_UNLOCK, itemLockVo.getCount(), "解锁订单号："+orderLockVo.getOrderNo());
			});
			//throw new WmsException(ResultCodeEnum.INVENTORY_LESS);
			return Result.build(null, ResultCodeEnum.INVENTORY_LESS);
		}

		// 如果所有商品都锁定成功的情况下，需要缓存锁定信息到redis。以方便将来解锁库存 或者 减库存
		// 以outOrderNo作为key，以goodsLockVoList锁定信息作为value
		this.redisTemplate.opsForValue().set(RedisConst.INVENTORY_INFO_PREFIX + orderLockVo.getOrderNo(), JSON.toJSONString(orderItemLockVoList));

		//清除业务goodsId列表
		orderResponseVoList.forEach(item -> item.setGoodsIdList(null));
		redisTemplate.opsForValue().set(keyList, JSON.toJSONString(orderResponseVoList), 1, TimeUnit.HOURS);
		return Result.ok(orderResponseVoList);
	}

	/**
	 * 仓库锁定货品库存
	 * @param warehouseId
	 * @param orderItemLockVoList
	 * @return
	 */
	private Boolean lock(String orderNo, Long warehouseId, List<OrderItemLockVo> orderItemLockVoList) {
		orderItemLockVoList.forEach(orderItemLockVo -> {
			orderItemLockVo.setWarehouseId(warehouseId);
			int lock = inventoryInfoMapper.lock(warehouseId, orderItemLockVo.getGoodsId(), orderItemLockVo.getCount());
			if(lock == 1) {
				orderItemLockVo.setLock(true);

				//记录日志
				invLogService.log(warehouseId, orderItemLockVo.getGoodsId(), InvLogType.OUT_LOCK, orderItemLockVo.getCount(), "锁定订单号："+orderNo);
			} else {
				orderItemLockVo.setLock(false);
			}
		});
		return true;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void unlock(String orderNo) {
		//业务去重，防止重复消费
		Boolean isRepeat = invBusinessRepeatService.isRepeat("unlock:" + orderNo, "订单解锁");
		if(isRepeat) return;

		// 获取锁定库存的缓存信息
		String orderItemLockVoListJson = (String)this.redisTemplate.opsForValue().get(RedisConst.INVENTORY_INFO_PREFIX + orderNo);
		if (StringUtils.isEmpty(orderItemLockVoListJson)){
			return ;
		}

		// 解锁库存
		List<OrderItemLockVo> orderItemLockVoList = JSON.parseArray(orderItemLockVoListJson, OrderItemLockVo.class);
		orderItemLockVoList.forEach(lockVo -> {
            int unLock = this.inventoryInfoMapper.unLock(lockVo.getWarehouseId(), lockVo.getGoodsId(), lockVo.getCount());
            if(unLock == 0) {
                throw new WmsException(ResultCodeEnum.DATA_ERROR);
            }

			//记录日志
			invLogService.log(lockVo.getWarehouseId(), lockVo.getGoodsId(), InvLogType.OUT_UNLOCK, lockVo.getCount(), "解锁订单号："+orderNo);
		});

		// 解锁库存之后，删除锁定库存的缓存。以防止重复解锁库存
		this.redisTemplate.delete(RedisConst.INVENTORY_INFO_PREFIX + orderNo);
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void minus(String outOrderNo) {
		//业务去重，防止重复消费
		Boolean isRepeat = invBusinessRepeatService.isRepeat("minus:" + outOrderNo, "出库单扣减库存");
		if(isRepeat) return;

		// 获取出库单信息
		OutOrder outOrder = outboundFeignClient.getByOutOrderNo(outOrderNo);
		if (null == outOrder || CollectionUtils.isEmpty(outOrder.getOutOrderItemList())){
			return;
		}

		//已减库存
		if (outOrder.getStatus() == OutOrderStatus.FINISH){
			return;
		}

		// 减库存
		outOrder.getOutOrderItemList().forEach(item -> {
			int minus = this.inventoryInfoMapper.minus(item.getWarehouseId(), item.getGoodsId(), item.getBuyCount());
            if(minus == 0) {
                throw new WmsException(ResultCodeEnum.DATA_ERROR);
            }

			//记录日志
			invLogService.log(item.getWarehouseId(), item.getGoodsId(), InvLogType.OUT_MINUS, item.getBuyCount(), "扣减库存，出库单号："+outOrder.getOutOrderNo());
		});

        //更新完成状态
        Boolean isUpdate = outboundFeignClient.updateFinishStatus(outOrder.getId());
        if(!isUpdate) {
            throw new WmsException(ResultCodeEnum.DATA_ERROR);
        }

		//扣减完库存，通知商城订单已发货
		rabbitService.sendMessage(MqConst.EXCHANGE_INVENTORY, MqConst.ROUTING_DELIVER, outOrder.getOrderNo());
	}

	@Override
	public Integer checkInventory(Long skuId) {
		GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfoBySkuId(skuId);
		if(null == goodsInfo) return 0;
		int count = this.count(new LambdaQueryWrapper<InventoryInfo>().eq(InventoryInfo::getGoodsId, goodsInfo.getId()).gt(InventoryInfo::getAvailableCount, 0));
		return count > 0 ? 1 : 0;
	}

	@Override
	public void updateInventory(InventoryFormVo inventoryFormVo) {
		InventoryInfo inventoryInfo = this.inventoryInfoMapper.getCheck(inventoryFormVo.getWarehouseId(), inventoryFormVo.getGoodsId());
		if(null != inventoryInfo) {
			inventoryInfoMapper.putaway(inventoryFormVo.getWarehouseId(), inventoryFormVo.getGoodsId(), inventoryFormVo.getCount());
			//记录日志
			invLogService.log(inventoryInfo.getWarehouseId(), inventoryInfo.getGoodsId(), InvLogType.HAND_PUTAWAY, inventoryFormVo.getCount(), "操作人：" + AuthContextHolder.getUserName());
		} else {
			throw new WmsException(ResultCodeEnum.DATA_ERROR);
		}
	}

	@Transactional(rollbackFor = {Exception.class})
    @Override
    public void picking(List<OutPickingItem> outPickingItemList) {
        outPickingItemList.forEach(item -> {
			inventoryInfoMapper.updatePicking(item.getWarehouseId(), item.getGoodsId(), item.getPickingCount());
		});
    }
}
