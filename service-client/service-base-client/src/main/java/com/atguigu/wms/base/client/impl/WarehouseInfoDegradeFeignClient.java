package com.atguigu.wms.base.client.impl;

import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WarehouseInfoDegradeFeignClient implements WarehouseInfoFeignClient {

	@Override
	public WarehouseInfo getWarehouseInfo(Long id) {
		return null;
	}

	@Override
	public String getNameById(Long id) {
		return "";
	}

	@Override
	public List<String> findNameByIdList(List<Long> idList) {
		return null;
	}

	@Override
	public List<Long> findPriorityWarehouseIdList(OutOrderAddressVo outOrderAddressVo) {
		return null;
	}
}

