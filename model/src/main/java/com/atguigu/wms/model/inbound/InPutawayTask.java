package com.atguigu.wms.model.inbound;

import com.atguigu.wms.enums.InPutawayTaskStatus;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InPutawayTask")
@TableName("in_putaway_task")
public class InPutawayTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	@TableField("task_no")
	private String taskNo;

	@ApiModelProperty(value = "inOrderId")
	@TableField("in_order_id")
	private Long inOrderId;

	@ApiModelProperty(value = "订单号")
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

	@ApiModelProperty(value = "指定仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "上架时间")
	@TableField("putaway_time")
	private Date putawayTime;

	@ApiModelProperty(value = "上架用户")
	@TableField("putaway_user_id")
	private Long putawayUserId;

	@ApiModelProperty(value = "上架用户")
	@TableField("putaway_user")
	private String putawayUser;

	@ApiModelProperty(value = "预期到货数")
	@TableField("expect_count")
	private Integer expectCount;

	@ApiModelProperty(value = "实际数量")
	@TableField("actual_count")
	private Integer actualCount;

	@ApiModelProperty(value = "不合格数量")
	@TableField("unqualified_count")
	private Integer unqualifiedCount;

	@ApiModelProperty(value = "上架数量")
	@TableField("putaway_count")
	private Integer putawayCount;

	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InPutawayTaskStatus status;

	@ApiModelProperty(value = "状态")
	@TableField(exist = false)
	private String statusName;

}