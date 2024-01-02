package com.atguigu.wms.model.outbound;

import com.atguigu.wms.enums.OutOrderItemStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "OutOrderItem")
@TableName("out_order_item")
public class OutOrderItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "出库单id")
	@TableField("out_order_id")
	private Long outOrderId;

	@ApiModelProperty(value = "skuId")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "货物id")
	@TableField("goods_id")
	private Long goodsId;

	@ApiModelProperty(value = "货物名称")
	@TableField("goods_name")
	private String goodsName;

	@ApiModelProperty(value = "货物条码")
	@TableField("barcode")
	private String barcode;

	@ApiModelProperty(value = "购买个数")
	@TableField("buy_count")
	private Integer buyCount;

	@ApiModelProperty(value = "重量")
	@TableField("weight")
	private String weight;

	@ApiModelProperty(value = "体积")
	@TableField("volume")
	private String volume;

	@ApiModelProperty(value = "指定仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "指定库区id")
	@TableField("storearea_id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	@TableField("storeshelf_id")
	private Long storeshelfId;

	@ApiModelProperty(value = "指定库位id")
	@TableField("storehouse_id")
	private Long storehouseId;

	@ApiModelProperty(value = "拣货任务id（批量拣货，标识哪个任务id）")
	@TableField("picking_task_id")
	private Long pickingTaskId;

	@ApiModelProperty(value = "状态（0：待拣货 1：已拣货）")
	@TableField("status")
	private OutOrderItemStatus status;

	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String storehouseName;
}