package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "GoodsType")
@TableName("goods_type")
public class GoodsType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "parentId")
	@TableField("parent_id")
	private Long parentId;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "下级机构")
	@TableField(exist = false)
	private List<GoodsType> children;

	@TableField(exist = false)
	private String value;
	@TableField(exist = false)
	private String label;
	@ApiModelProperty(value = "是否包含子节点")
	@TableField(exist = false)
	private boolean hasChildren;
}