package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.base.client.DictFeignClient;
import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InvCountingTaskStatus;
import com.atguigu.wms.enums.InvMoveTaskStatus;
import com.atguigu.wms.inventory.service.*;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.*;
import com.atguigu.wms.vo.inventory.InvMoveQueryVo;
import com.atguigu.wms.inventory.mapper.InvMoveMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvMoveServiceImpl extends ServiceImpl<InvMoveMapper, InvMove> implements InvMoveService {

	@Resource
	private InvMoveMapper invMoveMapper;

	@Resource
	private InventoryInfoService inventoryInfoService;

	@Resource
	private InvMoveItemService invMoveItemService;

	@Resource
	private InvMoveTaskService invMoveTaskService;

	@Resource
	private DictFeignClient dictFeignClient;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private StorehouseInfoFeignClient storehouseInfoFeignClient;

	@Resource
	private GoodsInfoFeignClient goodsInfoFeignClient;

	@Override
	public IPage<InvMove> selectPage(Page<InvMove> pageParam, InvMoveQueryVo invMoveQueryVo) {
		IPage<InvMove> page = invMoveMapper.selectPage(pageParam, invMoveQueryVo);
		page.getRecords().forEach(item -> {
			item.setReasonName(dictFeignClient.getNameById(item.getReasonId()));

			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getStorehouseId()));
			item.setStatusName(item.getStatus().getComment());
		});
		return page;
	}

	@Override
	public void saveInvMove(InvMove invMove) {
		invMove.setInvMoveNo(NoUtils.getOrderNo());
		this.save(invMove);

		List<InvMoveItem> invCountingItemList = invMove.getInvMoveItemList();
		for(InvMoveItem invMoveItem : invCountingItemList) {
			invMoveItem.setInvMoveId(invMove.getId());
			invMoveItemService.save(invMoveItem);
		}
	}

	@Override
	public void updateInvMove(InvMove invMove) {
		this.updateById(invMove);

		invMoveItemService.remove(new LambdaQueryWrapper<InvMoveItem>().eq(InvMoveItem::getInvMoveId, invMove.getId()));
		List<InvMoveItem> invCountingItemList = invMove.getInvMoveItemList();
		for(InvMoveItem invMoveItem : invCountingItemList) {
			invMoveItem.setInvMoveId(invMove.getId());
			invMoveItemService.save(invMoveItem);
		}
	}

	@Override
	public InvMove getInvMoveById(Long id) {
		InvMove invMove = this.getById(id);
		invMove.setReasonName(dictFeignClient.getNameById(invMove.getReasonId()));
		invMove.setStatusName(invMove.getStatus().getComment());
		invMove.setWarehouseName(warehouseInfoFeignClient.getNameById(invMove.getWarehouseId()));
		invMove.setStorehouseName(storehouseInfoFeignClient.getNameById(invMove.getStorehouseId()));

		List<InvMoveItem> invMoveItemList = invMoveItemService.findByInvMoveId(id);
		invMove.setInvMoveItemList(invMoveItemList);
		return invMove;
	}

	@Override
	public Map<String, Object> show(Long id) {
		InvMove invMove = this.getInvMoveById(id);
		List<InvMoveTask> invMoveTaskList = invMoveTaskService.findByInvMoveId(id).stream().filter(item -> item.getStatus() != InvMoveTaskStatus.PENDING_DEAL).collect(Collectors.toList());
		invMoveTaskList.forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});

		List<Long> goodsIdList = invMove.getInvMoveItemList().stream().map(InvMoveItem::getGoodsId).collect(Collectors.toList());
		List<InventoryInfo> inventoryInfoList = inventoryInfoService.list(new LambdaQueryWrapper<InventoryInfo>().in(InventoryInfo::getGoodsId, goodsIdList).eq(InventoryInfo::getWarehouseId, invMove.getWarehouseId()));
		inventoryInfoList.forEach(item -> {
			GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfo(item.getGoodsId());
			item.setGoodsInfo(goodsInfo);
		});

		Map<String, Object> map = new HashMap<>();
		map.put("invMove", invMove);
		map.put("inventoryInfoList", inventoryInfoList);
		map.put("invMoveTaskList", invMoveTaskList);
		return map;
	}

}
