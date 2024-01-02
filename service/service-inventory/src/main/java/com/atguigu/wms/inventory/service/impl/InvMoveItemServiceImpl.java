package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.model.inventory.InvMoveItem;
import com.atguigu.wms.inventory.mapper.InvMoveItemMapper;
import com.atguigu.wms.inventory.service.InvMoveItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvMoveItemServiceImpl extends ServiceImpl<InvMoveItemMapper, InvMoveItem> implements InvMoveItemService {

	@Resource
	private InvMoveItemMapper invMoveItemMapper;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private StorehouseInfoFeignClient storehouseInfoFeignClient;

	@Override
	public List<InvMoveItem> findByInvMoveId(Long invMoveId) {
		List<InvMoveItem> invMoveItemList = this.list(new LambdaQueryWrapper<InvMoveItem>().eq(InvMoveItem::getInvMoveId, invMoveId));
		invMoveItemList.forEach(item -> {

			item.setStorehouseName(storehouseInfoFeignClient.getNameById(item.getMoveStorehouseId()));
		});
		return invMoveItemList;
	}
}
