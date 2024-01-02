package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "GoodsInfo")
@TableName("goods_info")
public class GoodsInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "商品编码")
	@TableField("code")
	private String code;

	@ApiModelProperty(value = "条码")
	@TableField("barcode")
	private String barcode;

	@ApiModelProperty(value = "商品类型id")
	@TableField("goods_type_id")
	private Long goodsTypeId;

	@ApiModelProperty(value = "品牌")
	@TableField("brand_name")
	private String brandName;

	@ApiModelProperty(value = "货品的销售属性")
	@TableField("sale_attr")
	private String saleAttr;

	@ApiModelProperty(value = "检验类型")
	@TableField("inspect_type_id")
	private Long inspectTypeId;

	@ApiModelProperty(value = "温度类型id")
	@TableField("temperature_type_id")
	private Long temperatureTypeId;

	@ApiModelProperty(value = "体积")
	@TableField("volume")
	private String volume;

	@ApiModelProperty(value = "重量")
	@TableField("weight")
	private String weight;

	@ApiModelProperty(value = "单位（箱）")
	@TableField("unit_id")
	private Long unitId;

	@ApiModelProperty(value = "拆零最小单位（个）")
	@TableField("base_unit_id")
	private Long baseUnitId;

	@ApiModelProperty(value = "拆零数量")
	@TableField("base_count")
	private Integer baseCount;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private Long skuId;
	@TableField(exist = false)
	private String goodsTypeName;
	@TableField(exist = false)
	private String inspectTypeName;
	@TableField(exist = false)
	private String temperatureTypeName;
	@TableField(exist = false)
	private String unitName;
	@TableField(exist = false)
	private String baseUnitName;
	@TableField(exist = false)
	private List<InventoryInfo> inventoryInfoList;
}