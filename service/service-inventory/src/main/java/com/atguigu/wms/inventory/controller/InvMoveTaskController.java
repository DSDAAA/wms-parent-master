package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.model.inventory.InvCountingItem;
import com.atguigu.wms.model.inventory.InvMoveItem;
import com.atguigu.wms.model.inventory.InvMoveTask;
import com.atguigu.wms.vo.inventory.InvCountingTaskFormVo;
import com.atguigu.wms.vo.inventory.InvMoveTaskFormVo;
import com.atguigu.wms.vo.inventory.InvMoveTaskQueryVo;
import com.atguigu.wms.inventory.service.InvMoveTaskService;
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
@Api(value = "InvMoveTask管理", tags = "InvMoveTask管理")
@RestController
@RequestMapping(value="/admin/inventory/invMoveTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvMoveTaskController {
	
	@Resource
	private InvMoveTaskService invMoveTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "invMoveTaskVo", value = "查询对象", required = false)
		InvMoveTaskQueryVo invMoveTaskQueryVo) {
		Page<InvMoveTask> pageParam = new Page<>(page, limit);
		IPage<InvMoveTask> pageModel = invMoveTaskService.selectPage(pageParam, invMoveTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InvMoveTask invMoveTask = invMoveTaskService.getById(id);
		return Result.ok(invMoveTask);
	}

	@ApiOperation(value = "分配")
	@PostMapping("assign")
	public Result assign(@RequestBody InvMoveTaskFormVo invMoveTaskFormVo) {
		invMoveTaskService.assign(invMoveTaskFormVo);
		return Result.ok();
	}

	@ApiOperation(value = "更新录入数据")
	@PostMapping("update")
	public Result update(@RequestBody List<InvMoveItem> invMoveItemList) {
		invMoveTaskService.updateInvMoveItem(invMoveItemList);
		return Result.ok();
	}

	@ApiOperation(value = "移库任务同步库存")
	@GetMapping("syncInventory/{id}")
	public Result syncInventory(@PathVariable Long id) {
		invMoveTaskService.syncInventory(id);
		return Result.ok();
	}
}

