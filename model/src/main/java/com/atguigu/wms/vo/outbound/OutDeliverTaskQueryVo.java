package com.atguigu.wms.vo.outbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class OutDeliverTaskQueryVo {
	
	@ApiModelProperty(value = "任务编号")
	private String no;

	@ApiModelProperty(value = "指定仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;


}

