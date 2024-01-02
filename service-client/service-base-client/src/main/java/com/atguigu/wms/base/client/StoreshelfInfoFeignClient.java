package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.GoodsInfoDegradeFeignClient;
import com.atguigu.wms.base.client.impl.StoreshelfInfoDegradeFeignClient;
import com.atguigu.wms.model.base.StoreshelfInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author qy
 *
 */
@FeignClient(value = "service-base", fallback = StoreshelfInfoDegradeFeignClient.class)
public interface StoreshelfInfoFeignClient {


	@ApiOperation(value = "获取对象")
	@GetMapping("/admin/base/storeshelfInfo/getStoreshelfInfo/{id}")
	StoreshelfInfo getStoreshelfInfo(@PathVariable Long id);

	@ApiOperation(value = "获取名称")
	@GetMapping("/admin/base/storeshelfInfo/getNameById/{id}")
	String getNameById(@PathVariable Long id);
}

