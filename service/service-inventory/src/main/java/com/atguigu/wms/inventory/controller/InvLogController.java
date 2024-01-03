package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.inventory.InvLog;
import com.atguigu.wms.vo.inventory.InvLogQueryVo;
import com.atguigu.wms.inventory.service.InvLogService;
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
@Api(value = "InvLog管理", tags = "InvLog管理")
@RestController
@RequestMapping(value="/admin/inventory/invLog")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvLogController {
	
	@Resource
	private InvLogService invLogService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "invLogVo", value = "查询对象", required = false)
		InvLogQueryVo invLogQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			invLogQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InvLog> pageParam = new Page<>(page, limit);
		IPage<InvLog> pageModel = invLogService.selectPage(pageParam, invLogQueryVo);
		return Result.ok(pageModel);
	}

}

