package com.atguigu.wms.vo.inbound;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InReceivingItemVo {

	@ApiModelProperty(value = "货品id")
	private Long goodsId;

	@ApiModelProperty(value = "预期到货数量")
	private Integer expectCount;

	@ApiModelProperty(value = "实际到货数量")
	private Integer actualCount;

}

