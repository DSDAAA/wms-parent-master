package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.inventory.InvCountingItem;
import com.atguigu.wms.model.inventory.InvCountingTask;
import com.atguigu.wms.inventory.service.InvCountingTaskService;
import com.atguigu.wms.vo.inventory.InvCountingTaskFormVo;
import com.atguigu.wms.vo.inventory.InvCountingTaskQueryVo;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author Dunston
 *
 */
@Api(value = "InvCountingTask管理", tags = "InvCountingTask管理")
@RestController
@RequestMapping(value="/admin/inventory/invCountingTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvCountingTaskController {
	
	@Resource
	private InvCountingTaskService invCountingTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "invCountingTaskVo", value = "查询对象", required = false)
				InvCountingTaskQueryVo invCountingTaskQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			invCountingTaskQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InvCountingTask> pageParam = new Page<>(page, limit);
		IPage<InvCountingTask> pageModel = invCountingTaskService.selectPage(pageParam, invCountingTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InvCountingTask invCountingTask = invCountingTaskService.getById(id);
		return Result.ok(invCountingTask);
	}

	@ApiOperation(value = "分配")
	@PostMapping("assign")
	public Result assign(@RequestBody InvCountingTaskFormVo invCountingTaskFormVo) {
		invCountingTaskService.assign(invCountingTaskFormVo);
		return Result.ok();
	}

	@ApiOperation(value = "更新录入数据")
	@PostMapping("update")
	public Result update(@RequestBody List<InvCountingItem> invCountingItemList) {
		invCountingTaskService.updateInvCountingItem(invCountingItemList);
		return Result.ok();
	}

	@ApiOperation(value = "盘点任务同步库存")
	@GetMapping("syncInventory/{id}")
	public Result syncInventory(@PathVariable Long id) {
		invCountingTaskService.syncInventory(id);
		return Result.ok();
	}

}

