package com.atguigu.wms.model.inventory;

import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "InvLog")
@TableName("inv_log")
public class InvLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private java.lang.Long warehouseId;

	@ApiModelProperty(value = "商品id")
	@TableField("goods_id")
	private java.lang.Long goodsId;

	@ApiModelProperty(value = "类型（上架、盘点）")
	@TableField("log_type")
	private InvLogType logType;

	@ApiModelProperty(value = "操作库存（改变的）")
	@TableField("alteration_count")
	private java.lang.Integer alterationCount;

	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "状态")
	@TableField(exist = false)
	private String logTypeName;
	@TableField(exist = false)
	private String changeWayName;
	@TableField(exist = false)
	private String warehouseName;
}