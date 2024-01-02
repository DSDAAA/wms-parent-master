package com.atguigu.wms.vo.inventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class InvCountingTaskFormVo {

	@ApiModelProperty(value = "盘点单id")
	private Long invCountingId;

	@ApiModelProperty(value = "操作人用户id")
	private Long countingUserId;

	@ApiModelProperty(value = "操作人名称")
	private String countingUser;


}

