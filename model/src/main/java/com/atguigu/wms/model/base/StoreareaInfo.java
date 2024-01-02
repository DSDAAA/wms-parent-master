package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "StoreareaInfo")
@TableName("storearea_info")
public class StoreareaInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "库区类型（数据字典：发货区 、收货区 、储存区、暂存区、不良区）")
	@TableField("area_type_id")
	private Long areaTypeId;

	@ApiModelProperty(value = "面积")
	@TableField("built_area")
	private Integer builtArea;

	@ApiModelProperty(value = "管理人")
	@TableField("manage_id")
	private Long manageId;

	@ApiModelProperty(value = "manageName")
	@TableField("manage_name")
	private String manageName;

	@ApiModelProperty(value = "managePhone")
	@TableField("manage_phone")
	private String managePhone;

	@ApiModelProperty(value = "货架个数")
	@TableField("storeshelf_count")
	private Integer storeshelfCount;

	@ApiModelProperty(value = "库位个数")
	@TableField("storehouse_count")
	private Integer storehouseCount;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String areaTypeName;
}