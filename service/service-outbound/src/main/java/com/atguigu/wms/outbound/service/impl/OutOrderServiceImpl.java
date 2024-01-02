package com.atguigu.wms.outbound.service.impl;

import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.enums.OutDeliverTaskStatus;
import com.atguigu.wms.enums.OutOrderItemStatus;
import com.atguigu.wms.enums.OutOrderStatus;
import com.atguigu.wms.enums.OutPickingStatus;
import com.atguigu.wms.inventory.client.InventoryInfoFeignClient;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.model.outbound.OutDeliverTask;
import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.model.outbound.OutOrderItem;
import com.atguigu.wms.model.outbound.OutPickingTask;
import com.atguigu.wms.outbound.service.OutDeliverTaskService;
import com.atguigu.wms.outbound.service.OutOrderItemService;
import com.atguigu.wms.outbound.service.OutPickingTaskService;
import com.atguigu.wms.vo.outbound.OutOrderQueryVo;
import com.atguigu.wms.outbound.mapper.OutOrderMapper;
import com.atguigu.wms.outbound.service.OutOrderService;
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
public class OutOrderServiceImpl extends ServiceImpl<OutOrderMapper, OutOrder> implements OutOrderService {

	@Resource
	private OutOrderMapper outOrderMapper;

	@Resource
	private OutOrderItemService outOrderItemService;

	@Resource
	private GoodsInfoFeignClient goodsInfoFeignClient;

	@Resource
	private InventoryInfoFeignClient inventoryInfoFeignClient;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private OutDeliverTaskService outDeliverTaskService;

	@Resource
	private OutPickingTaskService outPickingTaskService;

	@Override
	public IPage<OutOrder> selectPage(Page<OutOrder> pageParam, OutOrderQueryVo outOrderQueryVo) {
		IPage<OutOrder> page = outOrderMapper.selectPage(pageParam, outOrderQueryVo);
		page.getRecords().forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
		});
		return page;
	}

	@Override
	public OutOrder getOutOrderById(Long id) {
		OutOrder outOrder = this.getById(id);
		outOrder.setStatusName(outOrder.getStatus().getComment());
		outOrder.setWarehouseName(warehouseInfoFeignClient.getNameById(outOrder.getWarehouseId()));

		List<OutOrderItem> outOrderItemList = outOrderItemService.findByOutOrderId(id);
		outOrder.setOutOrderItemList(outOrderItemList);
		return outOrder;
	}

	@Override
	public Map<String, Object> show(Long id) {
		OutOrder outOrder = this.getOutOrderById(id);
		List<OutPickingTask> outPickingTaskList = outPickingTaskService.findByOutOrderId(id).stream().filter(item -> item.getStatus() == OutPickingStatus.FINISH).collect(Collectors.toList());
		List<OutDeliverTask> outDeliverTaskList = outDeliverTaskService.findByOutOrderId(id).stream().filter(item -> item.getStatus() == OutDeliverTaskStatus.FINISH).collect(Collectors.toList());

		Map<String, Object> map = new HashMap<>();
		map.put("outOrder", outOrder);
		map.put("outPickingTaskList", outPickingTaskList);
		map.put("outDeliverTaskList", outDeliverTaskList);
		return map;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void saveOutOrderList(List<OutOrder> outOrderList) {
		outOrderList.forEach(item -> {
			this.saveOutOrder(item);
		});
	}

	@Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveOutOrder(OutOrder outOrder) {
		outOrder.setOutOrderNo(outOrder.getOrderNo());
		outOrder.setStatus(OutOrderStatus.PENDING_PICKING);
		this.save(outOrder);

		List<OutOrderItem> outOrderItemList = outOrder.getOutOrderItemList();
		outOrderItemList.forEach(item -> {
			GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfoBySkuId(item.getSkuId());
			item.setOutOrderId(outOrder.getId());
			item.setGoodsId(goodsInfo.getId());
			item.setGoodsName(goodsInfo.getName());
			item.setBarcode(goodsInfo.getBarcode());
			item.setWeight(goodsInfo.getWeight());
			item.setVolume(goodsInfo.getVolume());

			InventoryInfo inventoryInfo = inventoryInfoFeignClient.getByGoodsIdAndWarehouseId(goodsInfo.getId(), outOrder.getWarehouseId());
			item.setWarehouseId(inventoryInfo.getWarehouseId());
			item.setStoreareaId(inventoryInfo.getStoreareaId());
			item.setStoreshelfId(inventoryInfo.getStoreshelfId());
			item.setStorehouseId(inventoryInfo.getStorehouseId());

			item.setStatus(OutOrderItemStatus.PENDING_PICKING);
		});
		outOrderItemService.saveBatch(outOrderItemList);
    }

    @Override
    public void updateStatus(Long id, OutOrderStatus status) {
		OutOrder outOrder = new OutOrder();
		outOrder.setId(id);
		outOrder.setStatus(status);
		this.updateById(outOrder);
    }

	@Override
	public OutOrder getByOutOrderNo(String outOrderNo) {
		OutOrder outOrder = this.getOne(new LambdaQueryWrapper<OutOrder>().eq(OutOrder::getOutOrderNo, outOrderNo));
		outOrder.setStatusName(outOrder.getStatus().getComment());
		List<OutOrderItem> outOrderItemList = outOrderItemService.findByOutOrderId(outOrder.getId());
		outOrder.setOutOrderItemList(outOrderItemList);
		return outOrder;
	}

	@Override
	public Boolean updateFinishStatus(Long id) {
		this.updateStatus(id, OutOrderStatus.FINISH);
		return true;
	}


}
