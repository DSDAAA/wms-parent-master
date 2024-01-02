package com.atguigu.wms.vo.inbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InApproveTaskQueryVo {
	
	@ApiModelProperty(value = "相关单号")
	private String no;

	@ApiModelProperty(value = "状态")
	private Integer status;

	private String createTimeBegin;
	private String createTimeEnd;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;
}

