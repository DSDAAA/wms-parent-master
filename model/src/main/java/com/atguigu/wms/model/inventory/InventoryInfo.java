package com.atguigu.wms.model.inventory;

import com.atguigu.wms.model.BaseEntity;
import com.atguigu.wms.model.base.GoodsInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InventoryInfo")
@TableName("inventory_info")
public class InventoryInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id")
	@TableField("goods_id")
	private java.lang.Long goodsId;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private java.lang.Long warehouseId;

	@ApiModelProperty(value = "库区id")
	@TableField("storearea_id")
	private java.lang.Long storeareaId;

	@ApiModelProperty(value = "货架id")
	@TableField("storeshelf_id")
	private java.lang.Long storeshelfId;

	@ApiModelProperty(value = "库位id")
	@TableField("storehouse_id")
	private java.lang.Long storehouseId;

	@ApiModelProperty(value = "总库存")
	@TableField("total_count")
	private java.lang.Integer totalCount;

	@ApiModelProperty(value = "锁定库存")
	@TableField("lock_count")
	private java.lang.Integer lockCount;

	@ApiModelProperty(value = "可用库存")
	@TableField("available_count")
	private java.lang.Integer availableCount;

	@ApiModelProperty(value = "拣货下架数量")
	@TableField("picking_count")
	private java.lang.Integer pickingCount;

	@ApiModelProperty(value = "预警数量")
	@TableField("warning_count")
	private java.lang.Integer warningCount;

	@ApiModelProperty(value = "销售数量")
	@TableField("sale_count")
	private java.lang.Integer saleCount;

	@ApiModelProperty(value = "单位")
	@TableField("unit_id")
	private java.lang.Long unitId;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String warehouseName;

	@TableField(exist = false)
	private String storehouseName;
	@TableField(exist = false)
	private GoodsInfo goodsInfo;
}