package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.GoodsInfoDegradeFeignClient;
import com.atguigu.wms.base.client.impl.WareConfigDegradeFeignClient;
import com.atguigu.wms.model.base.WareConfig;
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
 * @author qy
 *
 */
@FeignClient(value = "service-base", fallback = WareConfigDegradeFeignClient.class)
public interface WareConfigFeignClient {

	@ApiOperation(value = "获取")
	@GetMapping("/admin/base/wareConfig/getWareConfig")
	WareConfig getWaveConfig();
}

