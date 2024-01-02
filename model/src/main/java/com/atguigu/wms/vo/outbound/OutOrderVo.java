package com.atguigu.wms.vo.outbound;

import com.atguigu.wms.enums.OutOrderStatus;
import com.atguigu.wms.model.BaseEntity;
import com.atguigu.wms.model.outbound.OutOrderItem;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "出库单")
public class OutOrderVo {

	@ApiModelProperty(value = "拆单前订单号（父订单号），未拆单为空")
	private String parentOrderNo;

	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "收货人")
	private String consignee;

	@ApiModelProperty(value = "收件人电话")
	private String consigneeTel;

	@ApiModelProperty(value = "provinceId")
	private Long provinceId;

	@ApiModelProperty(value = "cityId")
	private Long cityId;

	@ApiModelProperty(value = "areaId")
	private Long areaId;

	@ApiModelProperty(value = "送货地址")
	private String deliveryAddress;

	@ApiModelProperty(value = "订单备注")
	private String orderComment;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "出库单明细")
	private List<OutOrderItemVo> outOrderItemVoList;

}