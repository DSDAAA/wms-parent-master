package com.atguigu.wms.model.inbound;

import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "InOrder")
@TableName("in_order")
public class InOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

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

	@ApiModelProperty(value = "预计到达日")
	@TableField("estimated_arrival_time")
	private Date estimatedArrivalTime;

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

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InOrderStatus status;

	@TableField(exist = false)
	private List<InOrderItem> inOrderItemList;
	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private String warehouseName;
}