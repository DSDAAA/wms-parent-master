package com.atguigu.wms.model.inventory;

import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.enums.InvCountingTaskStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InvCountingTask")
@TableName("inv_counting_task")
public class InvCountingTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	@TableField("task_no")
	private java.lang.String taskNo;

	@ApiModelProperty(value = "盘点单号")
	@TableField("inv_counting_no")
	private java.lang.String invCountingNo;

	@ApiModelProperty(value = "盘点单id")
	@TableField("inv_counting_id")
	private java.lang.Long invCountingId;

	@ApiModelProperty(value = "操作人用户id")
	@TableField("counting_user_id")
	private java.lang.Long countingUserId;

	@ApiModelProperty(value = "操作人名称")
	@TableField("counting_user")
	private java.lang.String countingUser;

	@ApiModelProperty(value = "操作完成时间")
	@TableField("counting_complete_time")
	private Date countingCompleteTime;

	@ApiModelProperty(value = "remarks")
	@TableField("remarks")
	private java.lang.String remarks;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private java.lang.Long warehouseId;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InvCountingTaskStatus status;

	@TableField(exist = false)
	private String statusName;

}