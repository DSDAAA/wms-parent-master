package com.atguigu.wms.model.inventory;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "InvBusinessRepeat")
@TableName("inv_business_repeat")
public class InvBusinessRepeat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "业务去重编号")
	@TableField("business_no")
	private String businessNo;

	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

}