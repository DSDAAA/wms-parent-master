package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.StoreareaInfoDegradeFeignClient;
import com.atguigu.wms.model.base.StoreareaInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Dunston
 *
 */
@FeignClient(value = "service-base", fallback = StoreareaInfoDegradeFeignClient.class)
public interface StoreareaInfoFeignClient {

	@ApiOperation(value = "获取对象")
	@GetMapping("/admin/base/storeareaInfo/getStoreareaInfo/{id}")
	StoreareaInfo getStoreareaInfo(@PathVariable Long id);

	@ApiOperation(value = "获取名称")
	@GetMapping("/admin/base/storeareaInfo/getNameById/{id}")
	String getNameById(@PathVariable Long id);

}

