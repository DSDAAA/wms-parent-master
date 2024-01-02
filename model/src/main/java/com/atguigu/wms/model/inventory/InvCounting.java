package com.atguigu.wms.model.inventory;

import com.atguigu.wms.enums.InvCountingStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "InvCounting")
@TableName("inv_counting")
public class InvCounting extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "盘点单号")
	@TableField("inv_counting_no")
	private java.lang.String invCountingNo;

	@ApiModelProperty(value = "盘点原因（规划、差异）")
	@TableField("reason_id")
	private java.lang.Long reasonId;

	@ApiModelProperty(value = "计划盘点时间")
	@TableField("plan_counting_time")
	private Date planCountingTime;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private java.lang.Long warehouseId;

	@ApiModelProperty(value = "库区id")
	@TableField("storearea_id")
	private java.lang.Long storeareaId;

	@ApiModelProperty(value = "货架id")
	@TableField("storeshelf_id")
	private java.lang.Long storeshelfId;

	@ApiModelProperty(value = "库位id")
	@TableField("storehouse_id")
	private java.lang.Long storehouseId;

	@ApiModelProperty(value = "remarks")
	@TableField("remarks")
	private java.lang.String remarks;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InvCountingStatus status;

	@TableField(exist = false)
	private String reasonName;
	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String storehouseName;
	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private List<InvCountingItem> invCountingItemList;

}