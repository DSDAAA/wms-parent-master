package com.atguigu.wms.outbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.outbound.OutDeliverTask;
import com.atguigu.wms.vo.outbound.OutDeliverFormVo;
import com.atguigu.wms.vo.outbound.OutDeliverTaskQueryVo;
import com.atguigu.wms.outbound.service.OutDeliverTaskService;
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
 * @author Dunston
 *
 */
@Api(value = "OutDeliverTask管理", tags = "OutDeliverTask管理")
@RestController
@RequestMapping(value="/admin/outbound/outDeliverTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OutDeliverTaskController {
	
	@Resource
	private OutDeliverTaskService outDeliverTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "outDeliverTaskVo", value = "查询对象", required = false)
		OutDeliverTaskQueryVo outDeliverTaskQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			outDeliverTaskQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<OutDeliverTask> pageParam = new Page<>(page, limit);
		IPage<OutDeliverTask> pageModel = outDeliverTaskService.selectPage(pageParam, outDeliverTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		OutDeliverTask outDeliverTask = outDeliverTaskService.getById(id);
		return Result.ok(outDeliverTask);
	}

	@ApiOperation(value = "发货")
	@PostMapping("deliver")
	public Result deliver(@RequestBody OutDeliverFormVo outDeliverFormVo) {
		outDeliverTaskService.deliver(outDeliverFormVo);
		return Result.ok();
	}

}

