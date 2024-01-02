package com.atguigu.wms.vo.inbound;

import com.atguigu.wms.enums.InOrderStatus;
import io.swagger.models.auth.In;
import lombok.Data;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

@Data
public class InOrderQueryVo {
	
	@ApiModelProperty(value = "单号")
	private String no;

	@ApiModelProperty(value = "货主")
	private String shipperName;

	@ApiModelProperty(value = "预计到达日")
	private String estimatedArrivalTimeBegin;
	private String estimatedArrivalTimeEnd;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

	@ApiModelProperty(value = "状态")
	private Integer status;

}

