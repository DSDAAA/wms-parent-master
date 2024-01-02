package com.atguigu.wms.inventory.client;

import com.atguigu.wms.inventory.client.impl.InventoryInfoDegradeFeignClient;
import com.atguigu.wms.model.inventory.InventoryInfo;
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
 * @author qy
 *
 */
@FeignClient(value = "service-inventory", fallback = InventoryInfoDegradeFeignClient.class)
public interface InventoryInfoFeignClient {

	@ApiOperation(value = "上架同步库存")
	@PostMapping("/admin/inventory/inventoryInfo/syncInventory/{inOrderNo}")
	Boolean syncInventory(@PathVariable String inOrderNo, @RequestBody List<InventoryInfoVo> inventoryInfoVoList);

	@ApiOperation(value = "检查与锁定库存")
	@PostMapping("/admin/inventory/inventoryInfo/checkAndLockInventory")
	Map<Long, List<Long>> checkAndLockInventory(@RequestBody OrderLockVo orderLockVo);

	@ApiOperation(value = "获取库存信息")
	@GetMapping("/admin/inventory/inventoryInfo/getByGoodsIdAndWarehouseId/{goodsId}/{warehouseId}")
	InventoryInfo getByGoodsIdAndWarehouseId(@PathVariable Long goodsId, @PathVariable Long warehouseId);
}

