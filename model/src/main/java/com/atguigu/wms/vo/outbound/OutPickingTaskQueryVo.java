package com.atguigu.wms.vo.outbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class OutPickingTaskQueryVo {
	
	@ApiModelProperty(value = "拣货任务编号")
	private String taskNo;

	@ApiModelProperty(value = "指定仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "指定库区id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架号id")
	private Long storeshelfId;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

}

