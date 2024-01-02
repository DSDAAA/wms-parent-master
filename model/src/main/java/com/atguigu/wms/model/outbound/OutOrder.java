package com.atguigu.wms.model.outbound;

import com.atguigu.wms.enums.OutOrderStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "OutOrder")
@TableName("out_order")
public class OutOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "出库单号")
	@TableField("out_order_no")
	private String outOrderNo;

	@ApiModelProperty(value = "拆单前订单号（父订单号），未拆单为空")
	@TableField("parent_order_no")
	private String parentOrderNo;

	@ApiModelProperty(value = "订单号")
	@TableField("order_no")
	private String orderNo;

	@ApiModelProperty(value = "收货人")
	@TableField("consignee")
	private String consignee;

	@ApiModelProperty(value = "收件人电话")
	@TableField("consignee_tel")
	private String consigneeTel;

	@ApiModelProperty(value = "provinceId")
	@TableField("province_id")
	private Long provinceId;

	@ApiModelProperty(value = "cityId")
	@TableField("city_id")
	private Long cityId;

	@ApiModelProperty(value = "areaId")
	@TableField("area_id")
	private Long areaId;

	@ApiModelProperty(value = "送货地址")
	@TableField("delivery_address")
	private String deliveryAddress;

	@ApiModelProperty(value = "订单备注")
	@TableField("order_comment")
	private String orderComment;

	@ApiModelProperty(value = "物流单号")
	@TableField("tracking_no")
	private String trackingNo;

	@ApiModelProperty(value = "物流公司")
	@TableField("tracking_company")
	private String trackingCompany;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "状态（0：待拣货，1：拣货中 2：待发货 3：已发货）")
	@TableField("status")
	private OutOrderStatus status;

	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private List<OutOrderItem> outOrderItemList;

}