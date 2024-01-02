package com.atguigu.wms.vo.outbound;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "InventoryInfo")
public class OrderItemLockVo {

	@ApiModelProperty(value = "skuId")
	private Long skuId;

	@ApiModelProperty(value = "数量")
	private Integer count;

	@ApiModelProperty(value = "业务字段，非传")
	private Long goodsId;
	@ApiModelProperty(value = "业务字段，非传")
	private Long warehouseId;
	@ApiModelProperty(value = "业务字段，非传")
	private Boolean lock;// 锁定状态

}