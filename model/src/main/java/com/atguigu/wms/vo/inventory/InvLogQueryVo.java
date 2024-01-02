package com.atguigu.wms.vo.inventory;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InvLogQueryVo {
	
	@ApiModelProperty(value = "商品id")
	private java.lang.Long goodsId;

	@ApiModelProperty(value = "类型（上架、盘点）")
	private java.lang.String logType;

	private java.lang.String createTimeBegin;
	private java.lang.String createTimeEnd;

	@ApiModelProperty(value = "仓库id")
	private java.lang.Long warehouseId;

}

