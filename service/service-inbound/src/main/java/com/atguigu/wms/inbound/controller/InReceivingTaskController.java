package com.atguigu.wms.inbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.inbound.InReceivingTask;
import com.atguigu.wms.vo.inbound.InReceivingFormVo;
import com.atguigu.wms.vo.inbound.InReceivingTaskQueryVo;
import com.atguigu.wms.inbound.service.InReceivingTaskService;
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
@Api(value = "InReceivingTask管理", tags = "InReceivingTask管理")
@RestController
@RequestMapping(value="/admin/inbound/inReceivingTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InReceivingTaskController {
	
	@Resource
	private InReceivingTaskService inReceivingTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "inReceivingTaskVo", value = "查询对象", required = false)
		InReceivingTaskQueryVo inReceivingTaskQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			inReceivingTaskQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InReceivingTask> pageParam = new Page<>(page, limit);
		IPage<InReceivingTask> pageModel = inReceivingTaskService.selectPage(pageParam, inReceivingTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InReceivingTask inReceivingTask = inReceivingTaskService.getById(id);
		return Result.ok(inReceivingTask);
	}

	@ApiOperation(value = "收货")
	@PostMapping("receiving")
	public Result receiving(@RequestBody InReceivingFormVo inReceivingFormVo) {
		inReceivingTaskService.receiving(inReceivingFormVo);
		return Result.ok();
	}

}

