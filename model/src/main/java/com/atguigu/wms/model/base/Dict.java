package com.atguigu.wms.model.base;

import com.atguigu.wms.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * Dict
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "数据字典")
@TableName("dict_info")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "上级id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty(value = "是否包含子节点")
    @TableField(exist = false)
    private boolean hasChildren;

}