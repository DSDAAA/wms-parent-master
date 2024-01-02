package com.atguigu.wms.outbound.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.wms.common.constant.MqConst;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.service.RabbitService;
import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import com.atguigu.wms.vo.outbound.OutOrderItemVo;
import com.atguigu.wms.vo.outbound.OutOrderQueryVo;
import com.atguigu.wms.outbound.service.OutOrderService;
import com.atguigu.wms.vo.outbound.OutOrderVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "OutOrder管理", tags = "OutOrder管理")
@RestController
@RequestMapping(value="/admin/outbound/outOrder")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OutOrderController {
	
	@Resource
	private OutOrderService outOrderService;

	@Resource
	private RabbitService rabbitService;

	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(
		@ApiParam(name = "page", value = "当前页码", required = true)
		@PathVariable Long page,
	
		@ApiParam(name = "limit", value = "每页记录数", required = true)
		@PathVariable Long limit,
	
		@ApiParam(name = "outOrderVo", value = "查询对象", required = false)
		OutOrderQueryVo outOrderQueryVo) {
		if(null != AuthContextHolder.getWarehouseId()) {
			outOrderQueryVo.setWarehouseId(AuthContextHolder.getWarehouseId());
		}
		Page<OutOrder> pageParam = new Page<>(page, limit);
		IPage<OutOrder> pageModel = outOrderService.selectPage(pageParam, outOrderQueryVo);
		return Result.ok(pageModel);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		OutOrder outOrder = outOrderService.getOutOrderById(id);
		return Result.ok(outOrder);
	}

	@ApiOperation(value = "获取")
	@GetMapping("show/{id}")
	public Result show(@PathVariable Long id) {
		return Result.ok(outOrderService.show(id));
	}

	@ApiOperation(value = "获取")
	@GetMapping("getByOutOrderNo/{outOrderNo}")
	public OutOrder getByOutOrderNo(@PathVariable String outOrderNo) {
		return outOrderService.getByOutOrderNo(outOrderNo);
	}

	@ApiOperation(value = "更新完成状态")
	@GetMapping("updateFinishStatus/{id}")
	public Boolean updateFinishStatus(@PathVariable Long id) {
		return outOrderService.updateFinishStatus(id);
	}

	@ApiOperation(value = "发送消息")
	@PostMapping("send")
	public Result send(@RequestBody OutOrderVo outOrderVo) {
//		OutOrder outOrder = outOrderService.getOutOrderById(id);
//		List<OutOrderItemVo> list = outOrder.getOutOrderItemList().stream().map(item -> {
//			OutOrderItemVo outOrderItemVo = new OutOrderItemVo();
//			BeanUtils.copyProperties(item, outOrderItemVo);
//			return outOrderItemVo;
//		}).collect(Collectors.toList());
//		OutOrderVo outOrderVo = new OutOrderVo();
//		BeanUtils.copyProperties(outOrder, outOrderVo);
//		outOrderVo.setOutOrderItemVoList(list);
		rabbitService.sendMessage(MqConst.EXCHANGE_INVENTORY, MqConst.ROUTING_OUTBOUND, JSON.toJSONString(outOrderVo));
		return Result.ok();
	}


}

