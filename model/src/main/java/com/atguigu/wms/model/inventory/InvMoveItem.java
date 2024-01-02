package com.atguigu.wms.model.inventory;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InvMoveItem")
@TableName("inv_move_item")
public class InvMoveItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "移库单id")
	@TableField("inv_move_id")
	private Long invMoveId;

	@ApiModelProperty(value = "商品id")
	@TableField("goods_id")
	private Long goodsId;

	@ApiModelProperty(value = "移库仓库id")
	@TableField("move_warehouse_id")
	private Long moveWarehouseId;

	@ApiModelProperty(value = "移库库区id")
	@TableField("move_storearea_id")
	private Long moveStoreareaId;

	@ApiModelProperty(value = "移库货架id")
	@TableField("move_storeshelf_id")
	private Long moveStoreshelfId;

	@ApiModelProperty(value = "移库库位id")
	@TableField("move_storehouse_id")
	private Long moveStorehouseId;

	@ApiModelProperty(value = "原总库存")
	@TableField("total_count")
	private Integer totalCount;

	@ApiModelProperty(value = "移库总库存")
	@TableField("move_total_count")
	private Integer moveTotalCount;

	@ApiModelProperty(value = "差异量")
	@TableField("difference_count")
	private Integer differenceCount;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String storehouseName;
}