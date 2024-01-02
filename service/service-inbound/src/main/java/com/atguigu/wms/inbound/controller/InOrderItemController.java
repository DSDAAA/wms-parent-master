package com.atguigu.wms.inbound.controller;

import com.atguigu.wms.model.inbound.InOrderItem;
import com.atguigu.wms.vo.inbound.InOrderItemQueryVo;
import com.atguigu.wms.inbound.service.InOrderItemService;
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
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "InOrderItem管理", tags = "InOrderItem管理")
@RestController
@RequestMapping(value="/admin/inbound/inOrderItem")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InOrderItemController {
	
	@Resource
	private InOrderItemService inOrderItemService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "inOrderItemVo", value = "查询对象", required = false)
		InOrderItemQueryVo inOrderItemQueryVo) {
		Page<InOrderItem> pageParam = new Page<>(page, limit);
		IPage<InOrderItem> pageModel = inOrderItemService.selectPage(pageParam, inOrderItemQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InOrderItem inOrderItem = inOrderItemService.getById(id);
		return Result.ok(inOrderItem);
	}

	@ApiOperation(value = "新增")
	@PostMapping("save")
	public Result save(@RequestBody InOrderItem inOrderItem) {
		inOrderItemService.save(inOrderItem);
		return Result.ok();
	}

	@ApiOperation(value = "修改")
	@PutMapping("update")
	public Result updateById(@RequestBody InOrderItem inOrderItem) {
		inOrderItemService.updateById(inOrderItem);
		return Result.ok();
	}

	@ApiOperation(value = "删除")
	@DeleteMapping("remove/{id}")
	public Result remove(@PathVariable Long id) {
		inOrderItemService.removeById(id);
		return Result.ok();
	}

	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		inOrderItemService.removeByIds(idList);
		return Result.ok();
	}
}

