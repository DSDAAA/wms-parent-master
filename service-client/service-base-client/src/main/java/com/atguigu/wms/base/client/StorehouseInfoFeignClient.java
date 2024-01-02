package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.GoodsInfoDegradeFeignClient;
import com.atguigu.wms.base.client.impl.StorehouseInfoDegradeFeignClient;
import com.atguigu.wms.model.base.StorehouseInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *
 * @author qy
 *
 */
@FeignClient(value = "service-base", fallback = StorehouseInfoDegradeFeignClient.class)
public interface StorehouseInfoFeignClient {

	@ApiOperation(value = "获取对象")
	@GetMapping("/admin/base/storehouseInfo/getStorehouseInfo/{id}")
	StorehouseInfo getStorehouseInfo(@PathVariable Long id);

	@ApiOperation(value = "获取名称")
	@GetMapping("/admin/base/storehouseInfo/getNameById/{id}")
	String getNameById(@PathVariable Long id);

	@PostMapping("/admin/base/storehouseInfo/findNameByIdList")
	List<String> findNameByIdList(@RequestBody List<Long> idList);
}

