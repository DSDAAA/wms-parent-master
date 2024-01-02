package com.atguigu.wms.vo.outbound;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseVo {

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "仓库id对应的skuId列表")
	private List<Long> skuIdList;

	@ApiModelProperty(value = "业务字段（忽略）")
	private List<Long> goodsIdList;
}

