package com.atguigu.wms.model.inventory;

import com.atguigu.wms.enums.InvMoveTaskStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InvMoveTask")
@TableName("inv_move_task")
public class InvMoveTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	@TableField("task_no")
	private String taskNo;

	@ApiModelProperty(value = "移库单号")
	@TableField("inv_move_no")
	private String invMoveNo;

	@ApiModelProperty(value = "移库单id")
	@TableField("inv_move_id")
	private Long invMoveId;

	@ApiModelProperty(value = "操作人用户id")
	@TableField("move_user_id")
	private Long moveUserId;

	@ApiModelProperty(value = "操作人名称")
	@TableField("move_user")
	private String moveUser;

	@ApiModelProperty(value = "操作完成时间")
	@TableField("move_complete_time")
	private Date moveCompleteTime;

	@ApiModelProperty(value = "remarks")
	@TableField("remarks")
	private String remarks;

	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InvMoveTaskStatus status;

	@TableField(exist = false)
	private String statusName;

}