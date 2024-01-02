package com.atguigu.wms.vo.outbound;

import com.atguigu.wms.enums.OutOrderItemStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "OutOrderItem")
public class OutOrderItemVo {


	@ApiModelProperty(value = "skuId")
	private Long skuId;

	@ApiModelProperty(value = "购买个数")
	@TableField("count")
	private Integer count;

}