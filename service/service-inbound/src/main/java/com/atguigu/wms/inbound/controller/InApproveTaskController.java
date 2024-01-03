package com.atguigu.wms.inbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.model.inbound.InApproveTask;
import com.atguigu.wms.vo.inbound.InApproveFormVo;
import com.atguigu.wms.vo.inbound.InApproveTaskQueryVo;
import com.atguigu.wms.inbound.service.InApproveTaskService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author Dunston
 *
 */
@Api(value = "InApproveTask管理", tags = "InApproveTask管理")
@RestController
@RequestMapping(value="/admin/inbound/inApproveTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InApproveTaskController {
	
	@Resource
	private InApproveTaskService inApproveTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "inApproveTaskVo", value = "查询对象", required = false)
		InApproveTaskQueryVo inApproveTaskQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			inApproveTaskQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InApproveTask> pageParam = new Page<>(page, limit);
		IPage<InApproveTask> pageModel = inApproveTaskService.selectPage(pageParam, inApproveTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "提交审批")
	@GetMapping("submitApprove/{inOrderId}")
	public Result submitApprove(@PathVariable Long inOrderId) {
		inApproveTaskService.submitApprove(inOrderId);
		return Result.ok();
	}

	@ApiOperation(value = "审批")
	@PostMapping("approve")
	public Result approve(@RequestBody InApproveFormVo inApproveFromVo) {
		inApproveTaskService.approve(inApproveFromVo);
		return Result.ok();
	}
}

