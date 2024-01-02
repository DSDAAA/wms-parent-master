package com.atguigu.wms.vo.inventory;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InvCountingQueryVo {
	
	@ApiModelProperty(value = "盘点单号")
	private java.lang.String invCountingNo;

	@ApiModelProperty(value = "盘点原因（规划、差异）")
	private java.lang.Long reasonId;

	@ApiModelProperty(value = "计划盘点时间")
	private String planCountingTimeBegin;
	private String planCountingTimeEnd;

	@ApiModelProperty(value = "仓库id")
	private java.lang.Long warehouseId;

	@ApiModelProperty(value = "库区id")
	private java.lang.Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private java.lang.Long storeshelfId;

	@ApiModelProperty(value = "状态")
	private Integer status;

}

