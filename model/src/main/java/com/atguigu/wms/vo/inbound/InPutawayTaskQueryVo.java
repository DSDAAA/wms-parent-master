package com.atguigu.wms.vo.inbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InPutawayTaskQueryVo {

	@ApiModelProperty(value = "相关单号")
	private String no;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

}

