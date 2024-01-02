package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.model.inventory.InvLog;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.inventory.InvLogQueryVo;
import com.atguigu.wms.inventory.mapper.InvLogMapper;
import com.atguigu.wms.inventory.service.InvLogService;
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
public class InvLogServiceImpl extends ServiceImpl<InvLogMapper, InvLog> implements InvLogService {

	@Resource
	private InvLogMapper invLogMapper;

	@Resource
	private InventoryInfoService inventoryInfoService;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Override
	public IPage<InvLog> selectPage(Page<InvLog> pageParam, InvLogQueryVo invLogQueryVo) {
		IPage<InvLog> page = invLogMapper.selectPage(pageParam, invLogQueryVo);
		page.getRecords().forEach(item -> {
			item.setLogTypeName(item.getLogType().getComment());
			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
		});
		return page;
	}

	@Override
	public void log(Long warehouseId, Long goodsId, InvLogType invLogType, Integer alterationCount, String remarks) {
		InvLog invLog = new InvLog();
		invLog.setWarehouseId(warehouseId);
		invLog.setGoodsId(goodsId);
		invLog.setLogType(invLogType);
		invLog.setAlterationCount(alterationCount);
		invLog.setRemarks(remarks);
		this.save(invLog);
	}

}
