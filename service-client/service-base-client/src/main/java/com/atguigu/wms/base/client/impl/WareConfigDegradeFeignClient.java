package com.atguigu.wms.base.client.impl;

import com.atguigu.wms.base.client.WareConfigFeignClient;
import com.atguigu.wms.model.base.WareConfig;
import org.springframework.stereotype.Component;

@Component
public class WareConfigDegradeFeignClient implements WareConfigFeignClient {

	@Override
	public WareConfig getWaveConfig() {
		return null;
	}
}

