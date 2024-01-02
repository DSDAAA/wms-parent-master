package com.atguigu.wms.outbound.service.impl;

import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.enums.OutOrderItemStatus;
import com.atguigu.wms.model.outbound.OutOrderItem;
import com.atguigu.wms.vo.outbound.OutOrderItemQueryVo;
import com.atguigu.wms.outbound.mapper.OutOrderItemMapper;
import com.atguigu.wms.outbound.service.OutOrderItemService;
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

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OutOrderItemServiceImpl extends ServiceImpl<OutOrderItemMapper, OutOrderItem> implements OutOrderItemService {

	@Resource
	private OutOrderItemMapper outOrderItemMapper;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private StorehouseInfoFeignClient storehouseInfoFeignClient;

	@Override
	public IPage<OutOrderItem> selectPage(Page<OutOrderItem> pageParam, OutOrderItemQueryVo outOrderItemQueryVo) {

		return outOrderItemMapper.selectPage(pageParam, outOrderItemQueryVo);
	}

    @Override
    public List<OutOrderItem> findByOutOrderId(Long outOrderId) {
		List<OutOrderItem> outOrderItemList = this.list(new LambdaQueryWrapper<OutOrderItem>().eq(OutOrderItem::getOutOrderId, outOrderId));
		outOrderItemList.forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
			if(null != item.getWarehouseId()) {
				item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			}
			if(null != item.getStorehouseId()) {
				item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getStorehouseId()));
			}
		});
        return outOrderItemList;
    }

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void updateBatchStatusByPickingTaskId(Long pickingTaskId, OutOrderItemStatus status) {
		OutOrderItem outOrderItem = new OutOrderItem();
		outOrderItem.setStatus(status);
		this.update(outOrderItem, new LambdaQueryWrapper<OutOrderItem>().eq(OutOrderItem::getPickingTaskId, pickingTaskId));
	}

	@Override
	public void updateBatchStatusByOutOrderId(Long outOrderId, OutOrderItemStatus status) {
		OutOrderItem outOrderItem = new OutOrderItem();
		outOrderItem.setStatus(status);
		this.update(outOrderItem, new LambdaQueryWrapper<OutOrderItem>().eq(OutOrderItem::getOutOrderId, outOrderId));
	}

}
