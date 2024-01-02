package com.atguigu.wms.vo.inventory;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InvMoveQueryVo {
	
	@ApiModelProperty(value = "移库单号")
	private String invMoveNo;

	@ApiModelProperty(value = "移库原因（规划、差异）")
	private Long reasonId;

	@ApiModelProperty(value = "计划移库时间")
	private String planMoveTimeBegin;
	private String planMoveTimeEnd;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "库区id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private Long storeshelfId;

	@ApiModelProperty(value = "库位id")
	private Long storehouseId;

	@ApiModelProperty(value = "状态")
	private Integer status;

}

