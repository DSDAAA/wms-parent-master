package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "GoodsSkuRelation")
@TableName("goods_sku_relation")
public class GoodsSkuRelation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "货品id")
	@TableField("goods_id")
	private Long goodsId;

	@ApiModelProperty(value = "电商平台sku id")
	@TableField("sku_id")
	private Long skuId;

}