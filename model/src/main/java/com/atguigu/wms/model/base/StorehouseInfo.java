package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "StorehouseInfo")
@TableName("storehouse_info")
public class StorehouseInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "库区id")
	@TableField("storearea_id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	@TableField("storeshelf_id")
	private Long storeshelfId;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "面积")
	@TableField("built_area")
	private Integer builtArea;

	@ApiModelProperty(value = "列")
	@TableField("house_column")
	private Integer houseColumn;

	@ApiModelProperty(value = "层")
	@TableField("house_layer")
	private Integer houseLayer;

	@ApiModelProperty(value = "管理人")
	@TableField("manage_id")
	private Long manageId;

	@ApiModelProperty(value = "manageName")
	@TableField("manage_name")
	private String manageName;

	@ApiModelProperty(value = "managePhone")
	@TableField("manage_phone")
	private String managePhone;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String storeareaName;
	@TableField(exist = false)
	private String storeshelfName;
}