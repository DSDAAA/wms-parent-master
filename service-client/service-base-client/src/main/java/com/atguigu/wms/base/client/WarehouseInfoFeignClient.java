package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.GoodsInfoDegradeFeignClient;
import com.atguigu.wms.base.client.impl.WarehouseInfoDegradeFeignClient;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *
 * @author Dunston
 *
 */
@FeignClient(value = "service-base", fallback = WarehouseInfoDegradeFeignClient.class)
public interface WarehouseInfoFeignClient {

	@ApiOperation(value = "获取对象")
	@GetMapping("/admin/base/warehouseInfo/getWarehouseInfo/{id}")
	WarehouseInfo getWarehouseInfo(@PathVariable Long id);

	@ApiOperation(value = "获取名称")
	@GetMapping("/admin/base/warehouseInfo/getNameById/{id}")
	String getNameById(@PathVariable Long id);

	@PostMapping("/admin/base/warehouseInfo/findNameByIdList")
	List<String> findNameByIdList(@RequestBody List<Long> idList);

	@PostMapping("/admin/base/warehouseInfo/findPriorityWarehouseIdList")
	List<Long> findPriorityWarehouseIdList(@RequestBody OutOrderAddressVo outOrderAddressVo);
}

