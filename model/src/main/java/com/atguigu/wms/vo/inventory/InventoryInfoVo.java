package com.atguigu.wms.vo.inventory;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "InventoryInfo")
public class InventoryInfoVo {

	@ApiModelProperty(value = "商品id")
	private Long goodsId;

	@ApiModelProperty(value = "货品名称")
	private String goodsName;

	@ApiModelProperty(value = "商品code")
	private String goodsCode;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "库区id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private Long storeshelfId;

	@ApiModelProperty(value = "库位id")
	private Long storehouseId;

	@ApiModelProperty(value = "上架数量（拆分后的数量）")
	private Integer putawayCount;

	@ApiModelProperty(value = "单位")
	@TableField("unit_id")
	private Long unitId;

	@ApiModelProperty(value = "创建人")
	private Long createId;

	@ApiModelProperty(value = "创建人")
	private String createName;

}