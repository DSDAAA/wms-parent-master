package com.atguigu.wms.vo.inventory;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "InventoryInfo")
public class InventoryFormVo {

	@ApiModelProperty(value = "商品id")
	private Long goodsId;

	@ApiModelProperty(value = "仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "上架数量")
	private Integer count;

}