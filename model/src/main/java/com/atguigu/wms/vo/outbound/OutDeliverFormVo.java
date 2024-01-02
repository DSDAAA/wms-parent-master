package com.atguigu.wms.vo.outbound;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OutDeliverFormVo {
	
	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "物流单号")
	private String trackingNo;

	@ApiModelProperty(value = "物流公司")
	private String trackingCompany;

}

