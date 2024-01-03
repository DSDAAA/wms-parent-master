package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.StorehouseInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.StorehouseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author Dunston
 *
 */
@Api(value = "StorehouseInfo管理", tags = "StorehouseInfo管理")
@RestController
@RequestMapping(value="/admin/base/storehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StorehouseInfoController {

	@Resource
	private StorehouseInfoService storehouseInfoService;






	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		storehouseInfoService.removeByIds(idList);
		return Result.ok();
	}

	@ApiOperation(value = "获取")
	@GetMapping("findByStoreshelfId/{storeshelfId}")
	public Result findByStoreshelfId(@PathVariable Long storeshelfId) {
		return Result.ok(storehouseInfoService.findByStoreshelfId(storeshelfId));
	}

	@ApiOperation(value = "获取对象")
	@GetMapping("getStorehouseInfo/{id}")
	public StorehouseInfo getStorehouseInfo(@PathVariable Long id) {
		return storehouseInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return storehouseInfoService.getNameById(id);
	}

	@PostMapping("findNameByIdList")
	public List<String> findNameByIdList(@RequestBody List<Long> idList) {
		return storehouseInfoService.findNameByIdList(idList);
	}
}

