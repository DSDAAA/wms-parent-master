package com.atguigu.wms.vo.inventory;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InventoryInfoQueryVo {
	
	@ApiModelProperty(value = "商品id")
	private java.lang.Long goodsId;

	@ApiModelProperty(value = "货品名称")
	private java.lang.String goodsName;

	@ApiModelProperty(value = "商品code")
	private java.lang.String goodsCode;

	@ApiModelProperty(value = "货主id")
	private java.lang.Long shipperId;

	@ApiModelProperty(value = "仓库id")
	private java.lang.Long warehouseId;

	@ApiModelProperty(value = "库区id")
	private java.lang.Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private java.lang.Long storeshelfId;

	@ApiModelProperty(value = "库位id")
	private java.lang.Long storehouseId;

	@ApiModelProperty(value = "总库存")
	private java.lang.Integer totalCount;

	@ApiModelProperty(value = "锁定库存")
	private java.lang.Integer lockCount;

	@ApiModelProperty(value = "可用库存")
	private java.lang.Integer availableCount;

	@ApiModelProperty(value = "预警数量")
	private java.lang.Integer warningCount;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "创建人id")
	private java.lang.Long createId;

	@ApiModelProperty(value = "创建人名称")
	private java.lang.String createName;

	@ApiModelProperty(value = "最近更新人id")
	private java.lang.Long updateId;

	@ApiModelProperty(value = "最近更新人名称")
	private java.lang.String updateName;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

