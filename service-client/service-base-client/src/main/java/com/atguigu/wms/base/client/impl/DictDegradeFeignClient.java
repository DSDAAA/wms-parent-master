package com.atguigu.wms.base.client.impl;

import com.atguigu.wms.base.client.DictFeignClient;
import org.springframework.stereotype.Component;

@Component
public class DictDegradeFeignClient implements DictFeignClient {


	@Override
	public String getNameById(Long id) {
		return "";
	}
}

