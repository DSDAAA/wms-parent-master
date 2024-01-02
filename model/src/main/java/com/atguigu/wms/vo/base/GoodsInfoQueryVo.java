package com.atguigu.wms.vo.base;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class GoodsInfoQueryVo {
	

	@ApiModelProperty(value = "关键字")
	private String keyword;

	@ApiModelProperty(value = "商品类型id")
	private Long goodsTypeId;

	@ApiModelProperty(value = "温度类型")
	private Long temperatureTypeId;

	@ApiModelProperty(value = "状态")
	private Integer status;

}

