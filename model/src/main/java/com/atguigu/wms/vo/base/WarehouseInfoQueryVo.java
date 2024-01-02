package com.atguigu.wms.vo.base;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class WarehouseInfoQueryVo {

	private Long id;
	
	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "仓库类型：（1：中心仓库 2：区域仓库）")
	private Boolean type;

	@ApiModelProperty(value = "province")
	private Long provinceId;

	@ApiModelProperty(value = "市")
	private Long cityId;

	@ApiModelProperty(value = "区")
	private Long areaId;

}

