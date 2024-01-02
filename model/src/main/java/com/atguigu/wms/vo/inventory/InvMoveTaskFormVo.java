package com.atguigu.wms.vo.inventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvMoveTaskFormVo {

	@ApiModelProperty(value = "移库单id")
	private Long invMoveId;

	@ApiModelProperty(value = "操作人用户id")
	private Long moveUserId;

	@ApiModelProperty(value = "操作人名称")
	private String moveUser;


}

