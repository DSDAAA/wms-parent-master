package com.atguigu.wms.inbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.inbound.InPutawayTask;
import com.atguigu.wms.vo.inbound.InPutawayFormVo;
import com.atguigu.wms.vo.inbound.InPutawayTaskQueryVo;
import com.atguigu.wms.inbound.service.InPutawayTaskService;
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
@Api(value = "InPutawayTask管理", tags = "InPutawayTask管理")
@RestController
@RequestMapping(value="/admin/inbound/inPutawayTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InPutawayTaskController {

	@Resource
	private InPutawayTaskService inPutawayTaskService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
			@ApiParam(name = "page", value = "当前页码", required = true)
			@PathVariable Long page,

			@ApiParam(name = "limit", value = "每页记录数", required = true)
			@PathVariable Long limit,

			@ApiParam(name = "inPutawayTaskVo", value = "查询对象", required = false)
			InPutawayTaskQueryVo inPutawayTaskQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			inPutawayTaskQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InPutawayTask> pageParam = new Page<>(page, limit);
		IPage<InPutawayTask> pageModel = inPutawayTaskService.selectPage(pageParam, inPutawayTaskQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InPutawayTask inPutawayTask = inPutawayTaskService.getById(id);
		return Result.ok(inPutawayTask);
	}

	@ApiOperation(value = "上架")
	@PostMapping("putaway")
	public Result putaway(@RequestBody InPutawayFormVo inPutawayFormVo) {
		inPutawayTaskService.putaway(inPutawayFormVo);
		return Result.ok();
	}

	@ApiOperation(value = "同步库存")
	@GetMapping("syncInventory/{id}")
	public Result syncInventory(@PathVariable Long id) {
		inPutawayTaskService.syncInventory(id);
		return Result.ok();
	}
}
