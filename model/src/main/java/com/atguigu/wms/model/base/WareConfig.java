package com.atguigu.wms.model.base;

import com.atguigu.wms.enums.WareConfigDimension;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "WaveConfig")
@TableName("ware_config")
public class WareConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "波次间隔时间（分钟）")
	@TableField("interval_time")
	private Integer intervalTime;

	@ApiModelProperty(value = "维度（1：货架 2：库位 3：商品）")
	@TableField("dimension")
	private WareConfigDimension dimension;

	@ApiModelProperty(value = "库存解锁时间")
	@TableField("unlock_time")
	private Integer unlockTime;

	@ApiModelProperty(value = "关联商品url")
	@TableField("relation_url")
	private String relationUrl;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

}