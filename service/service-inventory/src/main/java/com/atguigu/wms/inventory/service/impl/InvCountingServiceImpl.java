package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.base.client.DictFeignClient;
import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InvCountingTaskStatus;
import com.atguigu.wms.inventory.service.InvCountingItemService;
import com.atguigu.wms.inventory.service.InvCountingTaskService;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.InvCounting;
import com.atguigu.wms.model.inventory.InvCountingItem;
import com.atguigu.wms.model.inventory.InvCountingTask;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.inventory.InvCountingQueryVo;
import com.atguigu.wms.inventory.mapper.InvCountingMapper;
import com.atguigu.wms.inventory.service.InvCountingService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvCountingServiceImpl extends ServiceImpl<InvCountingMapper, InvCounting> implements InvCountingService {

	@Resource
	private InvCountingMapper invCountingMapper;

	@Resource
	private InvCountingItemService invCountingItemService;

	@Resource
	private InventoryInfoService inventoryInfoService;

	@Resource
	private InvCountingTaskService invCountingTaskService;

	@Resource
	private DictFeignClient dictFeignClient;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private StorehouseInfoFeignClient storehouseInfoFeignClient;

	@Resource
	private GoodsInfoFeignClient goodsInfoFeignClient;

	@Override
	public IPage<InvCounting> selectPage(Page<InvCounting> pageParam, InvCountingQueryVo invCountingQueryVo) {
		IPage<InvCounting> page = invCountingMapper.selectPage(pageParam, invCountingQueryVo);
		page.getRecords().forEach(item -> {
			item.setReasonName(dictFeignClient.getNameById(item.getReasonId()));

			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getStorehouseId()));
			item.setStatusName(item.getStatus().getComment());
		});
		return page;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void saveInvCounting(InvCounting invCounting) {
		invCounting.setInvCountingNo(NoUtils.getOrderNo());
		invCounting.setCreateId(AuthContextHolder.getUserId());
		invCounting.setCreateName(AuthContextHolder.getUserName());
		this.save(invCounting);

		List<InvCountingItem> invCountingItemList = invCounting.getInvCountingItemList();
		for(InvCountingItem invCountingItem : invCountingItemList) {
			invCountingItem.setInvCountingId(invCounting.getId());
			invCountingItemService.save(invCountingItem);
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void updateInvCounting(InvCounting invCounting) {
		invCounting.setUpdateId(AuthContextHolder.getUserId());
		invCounting.setUpdateName(AuthContextHolder.getUserName());
		this.updateById(invCounting);

		invCountingItemService.remove(new LambdaQueryWrapper<InvCountingItem>().eq(InvCountingItem::getInvCountingId, invCounting.getId()));
		List<InvCountingItem> invCountingItemList = invCounting.getInvCountingItemList();
		for(InvCountingItem invCountingItem : invCountingItemList) {
			invCountingItem.setInvCountingId(invCounting.getId());

			invCountingItemService.save(invCountingItem);
		}
	}

	@Override
	public InvCounting getInvCountingById(Long id) {
		InvCounting invCounting = this.getById(id);
		invCounting.setReasonName(dictFeignClient.getNameById(invCounting.getReasonId()));
		invCounting.setStatusName(invCounting.getStatus().getComment());
		invCounting.setWarehouseName(warehouseInfoFeignClient.getNameById(invCounting.getWarehouseId()));
		invCounting.setStorehouseName(storehouseInfoFeignClient.getNameById(invCounting.getStorehouseId()));

		List<InvCountingItem> invCountingItemList = invCountingItemService.findByInvCountingId(id);
		invCounting.setInvCountingItemList(invCountingItemList);
		return invCounting;
	}

	@Override
	public Map<String, Object> show(Long id) {
		InvCounting invCounting = this.getInvCountingById(id);
		List<InvCountingTask> invCountingTaskList = invCountingTaskService.findByInvCountingId(id).stream().filter(item -> item.getStatus() != InvCountingTaskStatus.PENDING_DEAL).collect(Collectors.toList());
		invCountingTaskList.forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});

		List<Long> goodsIdList = invCounting.getInvCountingItemList().stream().map(InvCountingItem::getGoodsId).collect(Collectors.toList());
		List<InventoryInfo> inventoryInfoList = inventoryInfoService.list(new LambdaQueryWrapper<InventoryInfo>().in(InventoryInfo::getGoodsId, goodsIdList).eq(InventoryInfo::getWarehouseId, invCounting.getWarehouseId()));
		inventoryInfoList.forEach(item -> {
			GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfo(item.getGoodsId());
			item.setGoodsInfo(goodsInfo);
		});

		Map<String, Object> map = new HashMap<>();
		map.put("invCounting", invCounting);
		map.put("inventoryInfoList", inventoryInfoList);
		map.put("invCountingTaskList", invCountingTaskList);
		return map;
	}
}
