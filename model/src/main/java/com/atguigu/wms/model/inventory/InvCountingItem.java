package com.atguigu.wms.model.inventory;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InvCountingItem")
@TableName("inv_counting_item")
public class InvCountingItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "盘点单id")
	@TableField("inv_counting_id")
	private java.lang.Long invCountingId;

	@ApiModelProperty(value = "商品id")
	@TableField("goods_id")
	private java.lang.Long goodsId;

	@ApiModelProperty(value = "总库存")
	@TableField("total_count")
	private java.lang.Integer totalCount;

	@ApiModelProperty(value = "实际库存")
	@TableField("actual_count")
	private java.lang.Integer actualCount;

	@ApiModelProperty(value = "差异库存")
	@TableField("difference_count")
	private java.lang.Integer differenceCount;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

}