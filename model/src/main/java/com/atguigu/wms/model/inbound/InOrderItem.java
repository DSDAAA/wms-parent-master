package com.atguigu.wms.model.inbound;

import com.atguigu.wms.model.BaseEntity;
import com.atguigu.wms.model.base.GoodsInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "InOrderItem")
@TableName("in_order_item")
public class InOrderItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "入库单id")
	@TableField("in_order_id")
	private Long inOrderId;

	@ApiModelProperty(value = "货物id")
	@TableField("goods_id")
	private Long goodsId;

	@ApiModelProperty(value = "货物形态1:正常品，2：不良品")
	@TableField("goods_form")
	private Integer goodsForm;

	@ApiModelProperty(value = "单位（箱）")
	@TableField("unit_id")
	private Long unitId;

	@ApiModelProperty(value = "拆零后单位（个）")
	@TableField("base_unit_id")
	private Long baseUnitId;

	@ApiModelProperty(value = "预期到货数")
	@TableField("expect_count")
	private java.lang.Integer expectCount;

	@ApiModelProperty(value = "实际数量")
	@TableField("actual_count")
	private java.lang.Integer actualCount;

	@ApiModelProperty(value = "不合格数量")
	@TableField("unqualified_count")
	private java.lang.Integer unqualifiedCount;

	@ApiModelProperty(value = "上架数量")
	@TableField("putaway_count")
	private java.lang.Integer putawayCount;

	@ApiModelProperty(value = "单价")
	@TableField("price")
	private BigDecimal price;

	@ApiModelProperty(value = "拆分后实际库存数")
	@TableField("base_count")
	private java.lang.Integer baseCount;

	@ApiModelProperty(value = "金额")
	@TableField("total_price")
	private BigDecimal totalPrice;

	@ApiModelProperty(value = "生产日期")
	@TableField("produce_date")
	private Date produceDate;

	@ApiModelProperty(value = "到期日期")
	@TableField("due_date")
	private Date dueDate;

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

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private GoodsInfo goodsInfo;
	@TableField(exist = false)
	private String unitName;
	@TableField(exist = false)
	private String baseUnitName;
}