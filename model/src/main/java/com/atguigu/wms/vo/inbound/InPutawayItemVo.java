package com.atguigu.wms.vo.inbound;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InPutawayItemVo {

	@ApiModelProperty(value = "货品id")
	private Long goodsId;

	@ApiModelProperty(value = "实际数量")
	private java.lang.Integer actualCount;

	@ApiModelProperty(value = "不合格数量")
	private java.lang.Integer unqualifiedCount;

	@ApiModelProperty(value = "上架数量")
	private java.lang.Integer putawayCount;

	@ApiModelProperty(value = "指定仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "指定库区id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private Long storeshelfId;

	@ApiModelProperty(value = "指定库位id")
	private Long storehouseId;

}

