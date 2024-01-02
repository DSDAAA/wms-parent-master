package com.atguigu.wms.model.outbound;

import com.atguigu.wms.enums.OutPickingStatus;
import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "OutPickingTask")
@TableName("out_picking_task")
public class OutPickingTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "拣货任务编号")
	@TableField("task_no")
	private String taskNo;

	@ApiModelProperty(value = "指定仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "指定库区id")
	@TableField("storearea_id")
	private Long storeareaId;

	@ApiModelProperty(value = "货架号id")
	@TableField("storeshelf_id")
	private Long storeshelfId;

	@ApiModelProperty(value = "指定库位id")
	@TableField("storehouse_id")
	private Long storehouseId;

	@ApiModelProperty(value = "拣货时间")
	@TableField("picking_time")
	private Date pickingTime;

	@ApiModelProperty(value = "拣货用户")
	@TableField("picking_user_id")
	private Long pickingUserId;

	@ApiModelProperty(value = "拣货用户")
	@TableField("picking_user")
	private String pickingUser;

	@ApiModelProperty(value = "拣货数量")
	@TableField("picking_count")
	private Integer pickingCount;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private OutPickingStatus status;

	@TableField(exist = false)
	private String statusName;
	@TableField(exist = false)
	private String warehouseName;
	@TableField(exist = false)
	private String storeshelfName;
	@TableField(exist = false)
	private List<OutPickingItem> outPickingItemList;
}