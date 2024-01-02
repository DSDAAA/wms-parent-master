package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.vo.inventory.InventoryFormVo;
import com.atguigu.wms.vo.inventory.InventoryInfoQueryVo;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "InventoryInfo管理", tags = "InventoryInfo管理")
@RestController
@RequestMapping(value="/admin/inventory/inventoryInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InventoryInfoController {
	
	@Resource
	private InventoryInfoService inventoryInfoService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "goodsInfoQueryVo", value = "查询对象", required = false)
				GoodsInfoQueryVo goodsInfoQueryVo) {
		goodsInfoQueryVo.setStatus(1);
		Page<GoodsInfo> pageParam = new Page<>(page, limit);
		PageVo<GoodsInfo> pageModel = inventoryInfoService.selectPage(pageParam, goodsInfoQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InventoryInfo inventoryInfo = inventoryInfoService.getById(id);
		return Result.ok(inventoryInfo);
	}

	@ApiOperation(value = "手动上架库存")
	@PostMapping("updateInventory")
	public Result updateInventory(@RequestBody InventoryFormVo inventoryFormVo) {
		inventoryInfoService.updateInventory(inventoryFormVo);
		return Result.ok();
	}

	@ApiOperation(value = "上架同步库存")
	@PostMapping("syncInventory/{inOrderNo}")
	public Boolean syncInventory(@PathVariable String inOrderNo, @RequestBody List<InventoryInfoVo> inventoryInfoVoList) {
		return inventoryInfoService.syncInventory(inOrderNo, inventoryInfoVoList, InvLogType.PUTAWAY);
	}

	@ApiOperation(value = "根据库位获取")
	@GetMapping("findByStorehouseId/{storehouseId}")
	public Result findByStorehouseId(@PathVariable Long storehouseId) {
		return Result.ok(inventoryInfoService.findByStorehouseId(storehouseId));
	}

	@ApiOperation(value = "获取库存信息")
	@GetMapping("getByGoodsIdAndWarehouseId/{goodsId}/{warehouseId}")
	public InventoryInfo getByGoodsIdAndWarehouseId(@PathVariable Long goodsId, @PathVariable Long warehouseId) {
		return inventoryInfoService.getByGoodsIdAndWarehouseId(goodsId, warehouseId);
	}
}

