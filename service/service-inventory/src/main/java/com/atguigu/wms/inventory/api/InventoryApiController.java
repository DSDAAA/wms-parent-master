package com.atguigu.wms.inventory.api;

import com.alibaba.fastjson.JSON;
import com.atguigu.wms.common.constant.MqConst;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.service.RabbitService;
import com.atguigu.wms.common.util.GeneralConv;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import com.atguigu.wms.vo.outbound.OrderResponseVo;
import com.atguigu.wms.vo.outbound.OutOrderVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.internal.util.LinkedArrayList;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qy
 *
 */
@Api(tags = "库存管理")
@RestController
@RequestMapping(value="/api/inventory")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InventoryApiController {
	
	@Resource
	private InventoryInfoService inventoryInfoService;

	@Autowired
	private RestTemplate restTemplate;

	@Resource
	private RabbitService rabbitService;

	@ApiOperation(value = "检查库存")
	@GetMapping("check/{skuId}")
	public Result checkInventory(@PathVariable Long skuId) {
		return Result.ok(inventoryInfoService.checkInventory(skuId));
	}

	@ApiOperation(value = "检查与锁定库存")
	@PostMapping("checkAndLock")
	public Result<List<OrderResponseVo>> checkAndLock(@RequestBody OrderLockVo orderLockVo) {

//		String url = "http://139.198.152.148:8100/api/inventory/checkAndLock";
//		//定义head
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		//发送请求
//		ResponseEntity<Result> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(JSON.toJSONString(orderLockVo), headers), Result.class);
//		Result<List<OrderResponseVo>> result = exchange.getBody();
//		if(200 != result.getCode()) {
//			return result;
//		} else {
//			List<OrderResponseVo> orderResponseVoList = GeneralConv.convert2List(result.getData(), OrderResponseVo.class);
//			orderResponseVoList.forEach(item -> {
//				System.out.println(item.getWarehouseId());
//			});
//		}

//		return Result.ok();
		return inventoryInfoService.checkAndLock(orderLockVo);
	}

	@ApiOperation(value = "手动解锁库存")
	@GetMapping("unLock/{orderNo}")
	public Result unLock(@PathVariable String orderNo) {
		rabbitService.sendMessage(MqConst.EXCHANGE_INVENTORY, MqConst.ROUTING_UNLOCK, orderNo);
		return Result.ok();
	}

	@ApiOperation(value = "提交出库单")
	@PostMapping("submitOutbound")
	public Result submitOutbound(@RequestBody OutOrderVo outOrderVo) {
		rabbitService.sendMessage(MqConst.EXCHANGE_INVENTORY, MqConst.ROUTING_OUTBOUND, JSON.toJSONString(outOrderVo));
		return Result.ok();
	}

	@ApiOperation(value = "批量提交出库单（拆单场景）")
	@PostMapping("batchSubmitOutbound")
	public Result batchSubmitOutbound(@RequestBody List<OutOrderVo> outOrderVoList) {
		rabbitService.sendMessage(MqConst.EXCHANGE_INVENTORY, MqConst.ROUTING_OUTBOUND, JSON.toJSONString(outOrderVoList));
		return Result.ok();
	}
}

