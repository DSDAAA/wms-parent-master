package com.atguigu.wms.base.client.impl;

import com.atguigu.wms.base.client.StoreshelfInfoFeignClient;
import com.atguigu.wms.model.base.StoreshelfInfo;
import org.springframework.stereotype.Component;

@Component
public class StoreshelfInfoDegradeFeignClient implements StoreshelfInfoFeignClient {

	@Override
	public StoreshelfInfo getStoreshelfInfo(Long id) {
		return null;
	}

	@Override
	public String getNameById(Long id) {
		return "";
	}
}

