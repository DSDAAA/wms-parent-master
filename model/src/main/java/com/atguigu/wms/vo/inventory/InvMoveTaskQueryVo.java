package com.atguigu.wms.vo.inventory;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InvMoveTaskQueryVo {

	@ApiModelProperty(value = "任务编号")
	private java.lang.String no;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "仓库id")
	private java.lang.Long warehouseId;

}

