package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "ShipperInfo")
@TableName("shipper_info")
public class ShipperInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "phone")
	@TableField("phone")
	private String phone;

	@ApiModelProperty(value = "provinceId")
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

	@ApiModelProperty(value = "remark")
	@TableField("remark")
	private String remark;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String provinceName;
	@TableField(exist = false)
	private String cityName;
	@TableField(exist = false)
	private String areaName;
}