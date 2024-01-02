package com.atguigu.wms.vo.outbound;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderLockVo {
	
	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "省id，国家统计局三级地址数据")
	private Long provinceId;

	@ApiModelProperty(value = "送货地址")
	private String deliveryAddress;

	@ApiModelProperty(value = "仓库id，非传")
	private Long warehouseId;
	@ApiModelProperty(value = "订单明细")
	private List<OrderItemLockVo> orderItemVoList;

}

