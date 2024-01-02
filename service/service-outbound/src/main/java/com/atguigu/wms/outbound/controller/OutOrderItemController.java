package com.atguigu.wms.outbound.controller;

import com.atguigu.wms.model.outbound.OutOrderItem;
import com.atguigu.wms.vo.outbound.OutOrderItemQueryVo;
import com.atguigu.wms.outbound.service.OutOrderItemService;
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
@Api(value = "OutOrderItem管理", tags = "OutOrderItem管理")
@RestController
@RequestMapping(value="/admin/outbound/outOrderItem")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OutOrderItemController {
	
	@Resource
	private OutOrderItemService outOrderItemService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "outOrderItemVo", value = "查询对象", required = false)
		OutOrderItemQueryVo outOrderItemQueryVo) {
		Page<OutOrderItem> pageParam = new Page<>(page, limit);
		IPage<OutOrderItem> pageModel = outOrderItemService.selectPage(pageParam, outOrderItemQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		OutOrderItem outOrderItem = outOrderItemService.getById(id);
		return Result.ok(outOrderItem);
	}

	@ApiOperation(value = "新增")
	@PostMapping("save")
	public Result save(@RequestBody OutOrderItem outOrderItem) {
		outOrderItemService.save(outOrderItem);
		return Result.ok();
	}

	@ApiOperation(value = "修改")
	@PutMapping("update")
	public Result updateById(@RequestBody OutOrderItem outOrderItem) {
		outOrderItemService.updateById(outOrderItem);
		return Result.ok();
	}

	@ApiOperation(value = "删除")
	@DeleteMapping("remove/{id}")
	public Result remove(@PathVariable Long id) {
		outOrderItemService.removeById(id);
		return Result.ok();
	}

	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		outOrderItemService.removeByIds(idList);
		return Result.ok();
	}
}

