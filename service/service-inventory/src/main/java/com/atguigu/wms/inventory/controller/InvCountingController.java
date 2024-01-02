package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.inventory.InvCounting;
import com.atguigu.wms.vo.inventory.InvCountingQueryVo;
import com.atguigu.wms.inventory.service.InvCountingService;
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
@Api(value = "InvCounting管理", tags = "InvCounting管理")
@RestController
@RequestMapping(value="/admin/inventory/invCounting")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvCountingController {
	
	@Resource
	private InvCountingService invCountingService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "invCountingVo", value = "查询对象", required = false)
		InvCountingQueryVo invCountingQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			invCountingQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InvCounting> pageParam = new Page<>(page, limit);
		IPage<InvCounting> pageModel = invCountingService.selectPage(pageParam, invCountingQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InvCounting invCounting = invCountingService.getInvCountingById(id);
		return Result.ok(invCounting);
	}

	@ApiOperation(value = "获取")
	@GetMapping("show/{id}")
	public Result show(@PathVariable Long id) {
		return Result.ok(invCountingService.show(id));
	}

	@ApiOperation(value = "新增")
	@PostMapping("save")
	public Result save(@RequestBody InvCounting invCounting) {
		invCountingService.saveInvCounting(invCounting);
		return Result.ok();
	}

	@ApiOperation(value = "修改")
	@PutMapping("update")
	public Result updateById(@RequestBody InvCounting invCounting) {
		invCountingService.updateInvCounting(invCounting);
		return Result.ok();
	}

	@ApiOperation(value = "删除")
	@DeleteMapping("remove/{id}")
	public Result remove(@PathVariable Long id) {
		invCountingService.removeById(id);
		return Result.ok();
	}

}

