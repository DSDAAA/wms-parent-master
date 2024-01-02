package com.atguigu.wms.model.outbound;

import com.atguigu.wms.enums.OutDeliverTaskStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "OutDeliverTask")
@TableName("out_deliver_task")
public class OutDeliverTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	@TableField("task_no")
	private String taskNo;

	@ApiModelProperty(value = "出库单id")
	@TableField("out_order_id")
	private Long outOrderId;

	@ApiModelProperty(value = "出库单号")
	@TableField("out_order_no")
	private String outOrderNo;

	@ApiModelProperty(value = "指定仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "收货人")
	@TableField("consignee")
	private String consignee;

	@ApiModelProperty(value = "收件人电话")
	@TableField("consignee_tel")
	private String consigneeTel;

	@ApiModelProperty(value = "送货地址")
	@TableField("delivery_address")
	private String deliveryAddress;

	@ApiModelProperty(value = "订单备注")
	@TableField("order_comment")
	private String orderComment;

	@ApiModelProperty(value = "发货时间")
	@TableField("deliver_time")
	private Date deliverTime;

	@ApiModelProperty(value = "发货用户")
	@TableField("deliver_user_id")
	private Long deliverUserId;

	@ApiModelProperty(value = "发货用户")
	@TableField("deliver_user")
	private String deliverUser;

	@ApiModelProperty(value = "发货数量")
	@TableField("deliver_count")
	private Integer deliverCount;

	@ApiModelProperty(value = "物流单号")
	@TableField("tracking_no")
	private String trackingNo;

	@ApiModelProperty(value = "物流公司")
	@TableField("tracking_company")
	private String trackingCompany;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private OutDeliverTaskStatus status;

	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private String warehouseName;
}