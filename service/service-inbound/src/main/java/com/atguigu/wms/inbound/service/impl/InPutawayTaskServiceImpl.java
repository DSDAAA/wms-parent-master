package com.atguigu.wms.inbound.service.impl;

import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.enums.InPutawayTaskStatus;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.inbound.service.InOrderItemService;
import com.atguigu.wms.inbound.service.InOrderService;
import com.atguigu.wms.inventory.client.InventoryInfoFeignClient;
import com.atguigu.wms.model.inbound.*;
import com.atguigu.wms.vo.inbound.InPutawayFormVo;
import com.atguigu.wms.vo.inbound.InPutawayItemVo;
import com.atguigu.wms.vo.inbound.InPutawayTaskQueryVo;
import com.atguigu.wms.inbound.mapper.InPutawayTaskMapper;
import com.atguigu.wms.inbound.service.InPutawayTaskService;
import com.atguigu.wms.vo.inbound.InReceivingItemVo;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InPutawayTaskServiceImpl extends ServiceImpl<InPutawayTaskMapper, InPutawayTask> implements InPutawayTaskService {

	@Resource
	private InPutawayTaskMapper inPutawayTaskMapper;

	@Resource
	private InOrderService inOrderService;

	@Resource
	private InOrderItemService inOrderItemService;

	@Resource
	private InventoryInfoFeignClient inventoryInfoFeignClient;

	@Override
	public IPage<InPutawayTask> selectPage(Page<InPutawayTask> pageParam, InPutawayTaskQueryVo inPutawayTaskQueryVo) {
		IPage<InPutawayTask> page = inPutawayTaskMapper.selectPage(pageParam, inPutawayTaskQueryVo);
		page.getRecords().forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});
		return page;
	}

	@Override
	public List<InPutawayTask> findByInOrderid(Long inOrderId) {
		return this.list(new LambdaQueryWrapper<InPutawayTask>().eq(InPutawayTask::getInOrderId, inOrderId));
	}

//	@Transactional(rollbackFor = {Exception.class})
//	@Override
//	public void putaway(InPutawayFormVo inPutawayFormVo) {
//		InPutawayTask inPutawayTask = this.getById(inPutawayFormVo.getId());
//		inPutawayTask.setStatus(InPutawayTaskStatus.PENDING_INVENTORY);
//		inPutawayTask.setRemarks(inPutawayFormVo.getRemarks());
//		inPutawayTask.setPutawayTime(new Date());
//		inPutawayTask.setPutawayUserId(AuthContextHolder.getUserId());
//		inPutawayTask.setPutawayUser(AuthContextHolder.getUserName());
//		this.updateById(inPutawayTask);
//
//		InOrder inOrder = inOrderService.getById(inPutawayTask.getInOrderId());
//		inOrder.setStatus(InOrderStatus.INVENTORY_RUN);
//		inOrderService.updateById(inOrder);
//
//		int totalActualCount = 0;
//		int totalUnqualifiedCount = 0;
//		int totalPutawayCount = 0;
//		Map<Long, InPutawayItemVo> goodsIdToInPutawayItemVoMap = inPutawayFormVo.getInPutawayItemVoList().stream().collect(Collectors.toMap(InPutawayItemVo::getGoodsId, InPutawayItemVo -> InPutawayItemVo));
//		List<InOrderItem> inOrderItemList = inOrder.getInOrderItemList();
//		for(InOrderItem inOrderItem : inOrderItemList) {
//			InPutawayItemVo inPutawayItemVo = goodsIdToInPutawayItemVoMap.get(inOrderItem.getGoodsId());
//			inOrderItem.setActualCount(inPutawayItemVo.getActualCount());
//			inOrderItem.setUnqualifiedCount(inPutawayItemVo.getUnqualifiedCount());
//			inOrderItem.setPutawayCount(inPutawayItemVo.getPutawayCount());
//			inOrderItem.setBaseCount(inPutawayItemVo.getPutawayCount()*inOrderItem.getGoodsInfo().getBaseCount());
//			inOrderItem.setWarehouseId(inPutawayItemVo.getWarehouseId());
//			inOrderItem.setStoreareaId(inPutawayItemVo.getStoreareaId());
//			inOrderItem.setStoreshelfId(inPutawayItemVo.getStoreshelfId());
//			inOrderItem.setStorehouseId(inPutawayItemVo.getStorehouseId());
//			inOrderItemService.updateById(inOrderItem);
//
//			totalActualCount += inPutawayItemVo.getActualCount();
//			totalUnqualifiedCount += inPutawayItemVo.getUnqualifiedCount();
//			totalPutawayCount += inPutawayItemVo.getPutawayCount();
//		}
//		inPutawayTask.setActualCount(totalActualCount);
//		inPutawayTask.setUnqualifiedCount(totalUnqualifiedCount);
//		inPutawayTask.setPutawayCount(totalPutawayCount);
//		this.updateById(inPutawayTask);
//	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void putaway(InPutawayFormVo inPutawayFormVo) {
		InPutawayTask inPutawayTask = this.getById(inPutawayFormVo.getId());
		inPutawayTask.setStatus(InPutawayTaskStatus.FINISH);
		inPutawayTask.setRemarks(inPutawayFormVo.getRemarks());
		inPutawayTask.setPutawayTime(new Date());
		inPutawayTask.setPutawayUserId(AuthContextHolder.getUserId());
		inPutawayTask.setPutawayUser(AuthContextHolder.getUserName());
		this.updateById(inPutawayTask);

		InOrder inOrder = inOrderService.getById(inPutawayTask.getInOrderId());
		inOrder.setStatus(InOrderStatus.FINISH);
		inOrderService.updateById(inOrder);

		int totalActualCount = 0;
		int totalUnqualifiedCount = 0;
		int totalPutawayCount = 0;
		Map<Long, InPutawayItemVo> goodsIdToInPutawayItemVoMap = inPutawayFormVo.getInPutawayItemVoList().stream().collect(Collectors.toMap(InPutawayItemVo::getGoodsId, InPutawayItemVo -> InPutawayItemVo));
		List<InOrderItem> inOrderItemList = inOrder.getInOrderItemList();
		for(InOrderItem inOrderItem : inOrderItemList) {
			InPutawayItemVo inPutawayItemVo = goodsIdToInPutawayItemVoMap.get(inOrderItem.getGoodsId());
			inOrderItem.setActualCount(inPutawayItemVo.getActualCount());
			inOrderItem.setUnqualifiedCount(inPutawayItemVo.getUnqualifiedCount());
			inOrderItem.setPutawayCount(inPutawayItemVo.getPutawayCount());
			inOrderItem.setBaseCount(inPutawayItemVo.getPutawayCount()*inOrderItem.getGoodsInfo().getBaseCount());
			inOrderItem.setWarehouseId(inPutawayItemVo.getWarehouseId());
			inOrderItem.setStoreareaId(inPutawayItemVo.getStoreareaId());
			inOrderItem.setStoreshelfId(inPutawayItemVo.getStoreshelfId());
			inOrderItem.setStorehouseId(inPutawayItemVo.getStorehouseId());
			inOrderItemService.updateById(inOrderItem);

			totalActualCount += inPutawayItemVo.getActualCount();
			totalUnqualifiedCount += inPutawayItemVo.getUnqualifiedCount();
			totalPutawayCount += inPutawayItemVo.getPutawayCount();
		}
		inPutawayTask.setActualCount(totalActualCount);
		inPutawayTask.setUnqualifiedCount(totalUnqualifiedCount);
		inPutawayTask.setPutawayCount(totalPutawayCount);
		this.updateById(inPutawayTask);

		//更新库存
		List<InventoryInfoVo> inventoryInfoVoList = new ArrayList<>();
		for(InOrderItem inOrderItem : inOrder.getInOrderItemList()) {
			InventoryInfoVo inventoryInfoVo = new InventoryInfoVo();
			inventoryInfoVo.setGoodsId(inOrderItem.getGoodsId());
			inventoryInfoVo.setGoodsName(inOrderItem.getGoodsInfo().getName());
			inventoryInfoVo.setGoodsCode(inOrderItem.getGoodsInfo().getCode());
			inventoryInfoVo.setPutawayCount(inOrderItem.getBaseCount());
			inventoryInfoVo.setUnitId(inOrderItem.getBaseUnitId());
			inventoryInfoVo.setWarehouseId(inOrderItem.getWarehouseId());
			inventoryInfoVo.setStoreareaId(inOrderItem.getStoreareaId());
			inventoryInfoVo.setStoreshelfId(inOrderItem.getStoreshelfId());
			inventoryInfoVo.setStorehouseId(inOrderItem.getStorehouseId());
			inventoryInfoVo.setCreateId(AuthContextHolder.getUserId());
			inventoryInfoVo.setCreateName(AuthContextHolder.getUserName());
			inventoryInfoVoList.add(inventoryInfoVo);
		}
		//后续调整为seata分布式事务
		Boolean isPutaway = inventoryInfoFeignClient.syncInventory(inOrder.getInOrderNo(), inventoryInfoVoList);
		if(!isPutaway) {
			throw new WmsException(ResultCodeEnum.DATA_ERROR);
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void syncInventory(Long id) {
//		InPutawayTask inPutawayTask = this.getById(id);
//		inPutawayTask.setStatus(InPutawayTaskStatus.FINISH);
//		inPutawayTask.setUpdateId(AuthContextHolder.getUserId());
//		inPutawayTask.setUpdateName(AuthContextHolder.getUserName());
//		this.updateById(inPutawayTask);
//
//		InOrder inOrder = inOrderService.getById(inPutawayTask.getInOrderId());
//		inOrder.setStatus(InOrderStatus.FINISH);
//		inOrderService.updateById(inOrder);
//
//		//更新库存
//		List<InventoryInfoVo> inventoryInfoVoList = new ArrayList<>();
//		for(InOrderItem inOrderItem : inOrder.getInOrderItemList()) {
//			InventoryInfoVo inventoryInfoVo = new InventoryInfoVo();
//			inventoryInfoVo.setGoodsId(inOrderItem.getGoodsId());
//			inventoryInfoVo.setGoodsName(inOrderItem.getGoodsInfo().getName());
//			inventoryInfoVo.setGoodsCode(inOrderItem.getGoodsInfo().getCode());
//			inventoryInfoVo.setPutawayCount(inOrderItem.getBaseCount());
//			inventoryInfoVo.setUnitId(inOrderItem.getBaseUnitId());
//			inventoryInfoVo.setWarehouseId(inOrderItem.getWarehouseId());
//			inventoryInfoVo.setStoreareaId(inOrderItem.getStoreareaId());
//			inventoryInfoVo.setStoreshelfId(inOrderItem.getStoreshelfId());
//			inventoryInfoVo.setStorehouseId(inOrderItem.getStorehouseId());
//			inventoryInfoVo.setCreateId(AuthContextHolder.getUserId());
//			inventoryInfoVo.setCreateName(AuthContextHolder.getUserName());
//			inventoryInfoVoList.add(inventoryInfoVo);
//		}
//		//后续调整为seata分布式事务
//		Boolean isPutaway = inventoryInfoFeignClient.syncInventory(inOrder.getInOrderNo(), inventoryInfoVoList);
//		if(!isPutaway) {
//			throw new WmsException(ResultCodeEnum.DATA_ERROR);
//		}
	}

}
