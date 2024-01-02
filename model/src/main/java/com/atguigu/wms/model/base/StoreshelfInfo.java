package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "StoreshelfInfo")
@TableName("storeshelf_info")
public class StoreshelfInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "库区id")
	@TableField("storearea_id")
	private Long storeareaId;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "货架类型（数据字典：发货区 、收货区 、储存区、暂存区、不良区）")
	@TableField("house_type_id")
	private Long houseTypeId;

	@ApiModelProperty(value = "面积")
	@TableField("built_area")
	private Integer builtArea;

	@ApiModelProperty(value = "温度类型")
	@TableField("temperature_type_id")
	private Long temperatureTypeId;

	@ApiModelProperty(value = "管理人")
	@TableField("manage_id")
	private Long manageId;

	@ApiModelProperty(value = "manageName")
	@TableField("manage_name")
	private String manageName;

	@ApiModelProperty(value = "managePhone")
	@TableField("manage_phone")
	private String managePhone;

	@ApiModelProperty(value = "库位个数")
	@TableField("storehouse_count")
	private Integer storehouseCount;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String storeareaName;
	@TableField(exist = false)
	private String houseTypeName;
	@TableField(exist = false)
	private String temperatureTypeName;
}