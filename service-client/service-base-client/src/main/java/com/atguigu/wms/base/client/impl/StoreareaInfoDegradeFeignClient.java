package com.atguigu.wms.base.client.impl;

import com.atguigu.wms.base.client.StoreareaInfoFeignClient;
import com.atguigu.wms.model.base.StoreareaInfo;
import org.springframework.stereotype.Component;

@Component
public class StoreareaInfoDegradeFeignClient implements StoreareaInfoFeignClient {


	@Override
	public StoreareaInfo getStoreareaInfo(Long id) {
		return null;
	}

	@Override
	public String getNameById(Long id) {
		return "";
	}
}

