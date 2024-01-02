package com.atguigu.wms.vo.outbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class OutPickingItemQueryVo {
	
	@ApiModelProperty(value = "拣货任务id")
	private Long outPickingId;

	@ApiModelProperty(value = "货物id")
	private Long goodsId;

	@ApiModelProperty(value = "货物名称")
	private Integer goodsName;

	@ApiModelProperty(value = "货物条码")
	private String barcode;

	@ApiModelProperty(value = "拣货个数")
	private Integer pickingCount;

	@ApiModelProperty(value = "重量")
	private String weight;

	@ApiModelProperty(value = "体积")
	private String volume;

	@ApiModelProperty(value = "指定仓库id")
	private Long warehouseId;

	@ApiModelProperty(value = "指定库区id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	private Long storeshelfId;

	@ApiModelProperty(value = "指定库位id")
	private Long storehouseId;

	@ApiModelProperty(value = "状态（0：待拣货 1：已拣货）")
	private Integer status;

	@ApiModelProperty(value = "创建人id")
	private Long createId;

	@ApiModelProperty(value = "创建人名称")
	private String createName;

	@ApiModelProperty(value = "最近更新人id")
	private Long updateId;

	@ApiModelProperty(value = "最近更新人名称")
	private String updateName;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

