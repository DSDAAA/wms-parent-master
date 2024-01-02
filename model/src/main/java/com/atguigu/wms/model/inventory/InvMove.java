package com.atguigu.wms.model.inventory;

import com.atguigu.wms.enums.InvMoveStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "InvMove")
@TableName("inv_move")
public class InvMove extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "移库单号")
	@TableField("inv_move_no")
	private String invMoveNo;

	@ApiModelProperty(value = "移库原因（规划、差异）")
	@TableField("reason_id")
	private Long reasonId;

	@ApiModelProperty(value = "计划移库时间")
	@TableField("plan_move_time")
	private Date planMoveTime;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "库区id")
	@TableField("storearea_id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架id")
	@TableField("storeshelf_id")
	private Long storeshelfId;

	@ApiModelProperty(value = "库位id")
	@TableField("storehouse_id")
	private Long storehouseId;

	@ApiModelProperty(value = "操作人用户id")
	@TableField("move_user_id")
	private Long moveUserId;

	@ApiModelProperty(value = "操作人名称")
	@TableField("move_user")
	private String moveUser;

	@ApiModelProperty(value = "操作完成时间")
	@TableField("move_complete_time")
	private Date moveCompleteTime;

	@ApiModelProperty(value = "remarks")
	@TableField("remarks")
	private String remarks;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private InvMoveStatus status;

	@TableField(exist = false)
	private String reasonName;
	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String storehouseName;
	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private List<InvMoveItem> invMoveItemList;
}