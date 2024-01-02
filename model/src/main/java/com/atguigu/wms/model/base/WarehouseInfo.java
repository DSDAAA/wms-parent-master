package com.atguigu.wms.model.base;

import com.atguigu.wms.enums.WarehouseType;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "WarehouseInfo")
@TableName("warehouse_info")
public class WarehouseInfo extends BaseEntity implements Comparable<WarehouseInfo> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "仓库类型：（1：中心仓库 2：区域仓库）")
	@TableField("type")
	private Integer type;

	@ApiModelProperty(value = "province")
	@TableField("province_id")
	private Long provinceId;

	@ApiModelProperty(value = "市")
	@TableField("city_id")
	private Long cityId;

	@ApiModelProperty(value = "区")
	@TableField("area_id")
	private Long areaId;

	@ApiModelProperty(value = "address")
	@TableField("address")
	private String address;

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

	@ApiModelProperty(value = "库区个数")
	@TableField("storearea_count")
	private Integer storeareaCount;

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
	private String provinceName;
	@TableField(exist = false)
	private String cityName;
	@TableField(exist = false)
	private String areaName;
	@TableField(exist = false)
	private Double distance;

	@Override
	public int compareTo(WarehouseInfo o) {
		return (int) (this.getDistance() - o.getDistance());
	}
}