package com.atguigu.wms.vo.base;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class ShipperInfoQueryVo {
	
	@ApiModelProperty(value = "关键字")
	private String keyword;

	@ApiModelProperty(value = "provinceId")
	private Long provinceId;

	@ApiModelProperty(value = "市")
	private Long cityId;

	@ApiModelProperty(value = "区")
	private Long areaId;

}

