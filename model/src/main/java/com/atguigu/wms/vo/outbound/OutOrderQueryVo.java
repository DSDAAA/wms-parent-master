package com.atguigu.wms.vo.outbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class OutOrderQueryVo {

	@ApiModelProperty(value = "关键字")
	private String keyword;
	
	@ApiModelProperty(value = "相关单号")
	private String no;

	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "provinceId")
	private Long provinceId;

	@ApiModelProperty(value = "cityId")
	private Long cityId;

	@ApiModelProperty(value = "areaId")
	private Long areaId;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "状态（0：待拣货，1：拣货中 2：待发货 3：已发货）")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

}

