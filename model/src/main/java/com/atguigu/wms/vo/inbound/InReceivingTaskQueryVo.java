package com.atguigu.wms.vo.inbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InReceivingTaskQueryVo {

	@ApiModelProperty(value = "相关单号")
	private String no;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "预计到达时间")
	private String estimatedArrivalTimeBegin;
	private String estimatedArrivalTimeEnd;

	@ApiModelProperty(value = "状态")
	private Integer status;

}

