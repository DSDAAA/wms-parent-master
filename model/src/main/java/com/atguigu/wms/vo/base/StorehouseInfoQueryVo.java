package com.atguigu.wms.vo.base;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class StorehouseInfoQueryVo {
	
	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "库区id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private Long storeshelfId;

	@ApiModelProperty(value = "名称")
	private String name;

}

