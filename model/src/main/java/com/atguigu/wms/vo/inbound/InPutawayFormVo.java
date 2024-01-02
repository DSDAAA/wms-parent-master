package com.atguigu.wms.vo.inbound;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InPutawayFormVo {

	@ApiModelProperty(value = "任务id")
	private Long id;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "审批备注")
	private String remarks;

	//货品信息
	List<InPutawayItemVo> inPutawayItemVoList;


}

