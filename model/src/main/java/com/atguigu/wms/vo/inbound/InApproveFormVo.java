package com.atguigu.wms.vo.inbound;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InApproveFormVo {
	
	@ApiModelProperty(value = "任务id")
	private Long id;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "审批备注")
	private String remarks;

}

