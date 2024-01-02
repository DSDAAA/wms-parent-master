package com.atguigu.wms.model.inbound;

import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InApproveTask")
@TableName("in_approve_task")
public class InApproveTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	@TableField("task_no")
	private String taskNo;

	@ApiModelProperty(value = "入库单id")
	@TableField("in_order_id")
	private Long inOrderId;

	@ApiModelProperty(value = "入库单号")
	@TableField("in_order_no")
	private String inOrderNo;

	@ApiModelProperty(value = "货主订单号")
	@TableField("shipper_order_no")
	private String shipperOrderNo;

	@ApiModelProperty(value = "货主id")
	@TableField("shipper_id")
	private Long shipperId;

	@ApiModelProperty(value = "货主名称")
	@TableField("shipper_name")
	private String shipperName;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "司机")
	@TableField("driver")
	private String driver;

	@ApiModelProperty(value = "司机电话")
	@TableField("driver_phone")
	private String driverPhone;

	@ApiModelProperty(value = "预期到货数量")
	@TableField("expect_count")
	private java.lang.Integer expectCount;

	@ApiModelProperty(value = "核查用户")
	@TableField("approve_user")
	private String approveUser;

	@ApiModelProperty(value = "核查用户")
	@TableField("approve_user_id")
	private Long approveUserId;

	@ApiModelProperty(value = "核查时间")
	@TableField("approve_time")
	private Date approveTime;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InTaskStatus status;

	@ApiModelProperty(value = "审批配置")
	@TableField("remarks")
	private String remarks;

	@ApiModelProperty(value = "状态")
	@TableField(exist = false)
	private String statusName;
}