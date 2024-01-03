package com.atguigu.wms.inbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.model.inbound.InOrder;
import com.atguigu.wms.vo.inbound.InOrderQueryVo;
import com.atguigu.wms.inbound.service.InOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.AuthContext;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author Dunston
 *
 */
@Api(value = "InOrder管理", tags = "InOrder管理")
@RestController
@RequestMapping(value="/admin/inbound/inOrder")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InOrderController {
	
	@Resource
	private InOrderService inOrderService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "inOrderVo", value = "查询对象", required = false)
		InOrderQueryVo inOrderQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			inOrderQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<InOrder> pageParam = new Page<>(page, limit);
		IPage<InOrder> pageModel = inOrderService.selectPage(pageParam, inOrderQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		InOrder inOrder = inOrderService.getById(id);
		return Result.ok(inOrder);
	}

	@ApiOperation(value = "获取")
	@GetMapping("show/{id}")
	public Result show(@PathVariable Long id) {
		return Result.ok(inOrderService.show(id));
	}

	@ApiOperation(value = "新增")
	@PostMapping("save")
	public Result save(@RequestBody InOrder inOrder) {
		inOrderService.saveInOrder(inOrder);
		return Result.ok();
	}

	@ApiOperation(value = "修改")
	@PutMapping("update")
	public Result updateById(@RequestBody InOrder inOrder) {
		inOrderService.updateInOrder(inOrder);
		return Result.ok();
	}

	@ApiOperation(value = "删除")
	@DeleteMapping("remove/{id}")
	public Result remove(@PathVariable Long id) {
		inOrderService.removeById(id);
		return Result.ok();
	}

	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		inOrderService.removeByIds(idList);
		return Result.ok();
	}

}

