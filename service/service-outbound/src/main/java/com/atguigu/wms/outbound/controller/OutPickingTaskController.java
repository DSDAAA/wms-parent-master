package com.atguigu.wms.outbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.outbound.OutPickingTask;
import com.atguigu.wms.vo.outbound.OutPickingTaskQueryVo;
import com.atguigu.wms.outbound.service.OutPickingTaskService;
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
@Api(value = "OutPickingTask管理", tags = "OutPickingTask管理")
@RestController
@RequestMapping(value="/admin/outbound/outPickingTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OutPickingTaskController {
	
	@Resource
	private OutPickingTaskService outPickingTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "outPickingTaskVo", value = "查询对象", required = false)
		OutPickingTaskQueryVo outPickingTaskQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			outPickingTaskQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<OutPickingTask> pageParam = new Page<>(page, limit);
		IPage<OutPickingTask> pageModel = outPickingTaskService.selectPage(pageParam, outPickingTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		OutPickingTask outPickingTask = outPickingTaskService.getOutPickingTask(id);
		return Result.ok(outPickingTask);
	}

	@ApiOperation(value = "完成拣货")
	@GetMapping("finish/{id}")
	public Result finish(@PathVariable Long id) {
		outPickingTaskService.finish(id);
		return Result.ok();
	}
}

