package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.model.inventory.InvMove;
import com.atguigu.wms.vo.inventory.InvMoveQueryVo;
import com.atguigu.wms.inventory.service.InvMoveService;
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
@Api(value = "InvMove管理", tags = "InvMove管理")
@RestController
@RequestMapping(value="/admin/inventory/invMove")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvMoveController {
	
	@Resource
	private InvMoveService invMoveService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "invMoveVo", value = "查询对象", required = false)
		InvMoveQueryVo invMoveQueryVo) {
		Page<InvMove> pageParam = new Page<>(page, limit);
		IPage<InvMove> pageModel = invMoveService.selectPage(pageParam, invMoveQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InvMove invMove = invMoveService.getInvMoveById(id);
		return Result.ok(invMove);
	}

	@ApiOperation(value = "获取")
	@GetMapping("show/{id}")
	public Result show(@PathVariable Long id) {
		return Result.ok(invMoveService.show(id));
	}


	@ApiOperation(value = "新增")
	@PostMapping("save")
	public Result save(@RequestBody InvMove invMove) {
		invMoveService.saveInvMove(invMove);
		return Result.ok();
	}

	@ApiOperation(value = "修改")
	@PutMapping("update")
	public Result updateById(@RequestBody InvMove invMove) {
		invMoveService.updateInvMove(invMove);
		return Result.ok();
	}

	@ApiOperation(value = "删除")
	@DeleteMapping("remove/{id}")
	public Result remove(@PathVariable Long id) {
		invMoveService.removeById(id);
		return Result.ok();
	}

}

