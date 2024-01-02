package com.atguigu.wms.model.inventory;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Data
@ApiModel(description = "InvStatistics")
@TableName("inv_statistics")
public class InvStatistics extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "仓库id")
	@TableField("warehouse_id")
	private Long warehouseId;

	@ApiModelProperty(value = "入库统计")
	@TableField("in_count")
	private Integer inCount;

	@ApiModelProperty(value = "出库统计")
	@TableField("out_count")
	private Integer outCount;

	@ApiModelProperty(value = "维度按天")
	@TableField("day")
	private Date day;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private String month;

//	private String getMonth() {
//		return new DateTime(this.day).toString("yyyy-MM");
//	}

}