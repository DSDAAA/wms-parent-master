package com.atguigu.wms.vo.inbound;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class InOrderItemQueryVo {
	
	@ApiModelProperty(value = "入库单id")
	private Long inOrderId;

	@ApiModelProperty(value = "货主id")
	private Long shipperId;

	@ApiModelProperty(value = "货物id")
	private Long goodId;

	@ApiModelProperty(value = "货物形态1:正常品，2：不良品")
	private Integer goodForm;

	@ApiModelProperty(value = "单位（箱）")
	private String unit;

	@ApiModelProperty(value = "拆零后单位（个）")
	private String baseUnit;

	@ApiModelProperty(value = "预期到货数")
	private java.lang.Integer expectCount;

	@ApiModelProperty(value = "实际数量")
	private java.lang.Integer actualCount;

	@ApiModelProperty(value = "不合格数量")
	private java.lang.Integer unqualifiedCount;

	@ApiModelProperty(value = "上架数量")
	private java.lang.Integer putawayCount;

	@ApiModelProperty(value = "价格")
	private String price;

	@ApiModelProperty(value = "拆分后实际库存数")
	private java.lang.Integer baseCount;

	@ApiModelProperty(value = "拆分后价格")
	private String basePrice;

	@ApiModelProperty(value = "金额")
	private String totalPrice;

	@ApiModelProperty(value = "生产日期")
	private Date produceDate;

	@ApiModelProperty(value = "到期日期")
	private Date dueDate;

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

	@ApiModelProperty(value = "状态")
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

