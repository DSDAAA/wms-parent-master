package com.atguigu.wms.outbound.client;

import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.outbound.client.impl.OutboundDegradeFeignClient;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Dunston
 *
 */
@FeignClient(value = "service-outbound", fallback = OutboundDegradeFeignClient.class)
public interface OutboundFeignClient {

	@ApiOperation(value = "获取")
	@GetMapping("/admin/outbound/outOrder/getByOutOrderNo/{outOrderNo}")
	OutOrder getByOutOrderNo(@PathVariable String outOrderNo);

	@ApiOperation(value = "更新完成状态")
	@GetMapping("/admin/outbound/outOrder/updateFinishStatus/{id}")
	Boolean updateFinishStatus(@PathVariable Long id);
}

