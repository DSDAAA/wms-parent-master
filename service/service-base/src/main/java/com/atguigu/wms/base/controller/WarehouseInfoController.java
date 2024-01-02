package com.atguigu.wms.base.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "WarehouseInfo管理", tags = "WarehouseInfo管理")
@RestController
@RequestMapping(value="/admin/base/warehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class WarehouseInfoController {
	
	@Resource
	private WarehouseInfoService warehouseInfoService;



	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		warehouseInfoService.removeByIds(idList);
		return Result.ok();
	}


	@ApiOperation(value = "查询库位节点")
	@GetMapping("findNodes")
	public Result findNodes() {
		return Result.ok(warehouseInfoService.findNodes());
	}

	@ApiOperation(value = "获取对象")
	@GetMapping("getWarehouseInfo/{id}")
	public WarehouseInfo getWarehouseInfo(@PathVariable Long id) {
		return warehouseInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return warehouseInfoService.getNameById(id);
	}

	@PostMapping("findNameByIdList")
	public List<String> findNameByIdList(@RequestBody List<Long> idList) {
		return warehouseInfoService.findNameByIdList(idList);
	}

	@ApiOperation(value = "根据用户地址给满足条件的仓库指定优先级")
	@PostMapping("findPriorityWarehouseIdList")
	public List<Long> findNameByIdList(@RequestBody OutOrderAddressVo outOrderAddressVo) {
		return warehouseInfoService.findPriorityWarehouseIdList(outOrderAddressVo);
	}
}

