package com.atguigu.wms.outbound.service.impl;

import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.base.client.StoreshelfInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.model.outbound.OutPickingItem;
import com.atguigu.wms.vo.outbound.OutPickingItemQueryVo;
import com.atguigu.wms.outbound.mapper.OutPickingItemMapper;
import com.atguigu.wms.outbound.service.OutPickingItemService;
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
public class OutPickingItemServiceImpl extends ServiceImpl<OutPickingItemMapper, OutPickingItem> implements OutPickingItemService {

	@Resource
	private OutPickingItemMapper outPickingItemMapper;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private StorehouseInfoFeignClient storehouseInfoFeignClient;

	@Override
	public List<OutPickingItem> findByOutPickingId(Long outPickingId) {
		List<OutPickingItem> outPickingItemList = this.list(new LambdaQueryWrapper<OutPickingItem>().eq(OutPickingItem::getOutPickingId, outPickingId));
		outPickingItemList.forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getStorehouseId()));
		});
		return outPickingItemList;
	}

}
