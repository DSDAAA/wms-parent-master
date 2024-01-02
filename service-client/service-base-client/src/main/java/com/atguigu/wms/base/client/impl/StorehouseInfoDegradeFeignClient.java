package com.atguigu.wms.base.client.impl;

import com.atguigu.wms.base.client.StorehouseInfoFeignClient;
import com.atguigu.wms.model.base.StorehouseInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StorehouseInfoDegradeFeignClient implements StorehouseInfoFeignClient {

	@Override
	public StorehouseInfo getStorehouseInfo(Long id) {
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
}

