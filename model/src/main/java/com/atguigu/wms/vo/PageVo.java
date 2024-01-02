package com.atguigu.wms.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果包装
 *
 * @author itcast
 */
@Data
@ApiModel(value = "分页数据消息体", description = "分页数据统一对象")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PageVo<T> implements Serializable {

    @ApiModelProperty(value = "总条目数", required = true)
    private Long total;

    @ApiModelProperty(value = "页尺寸", required = true)
    private Long size;

    @ApiModelProperty(value = "总页数", required = true)
    private Long pages;

    @ApiModelProperty(value = "当前页码", required = true)
    private long current;

    @ApiModelProperty(value = "数据列表", required = true)
    private List<T> records;

    public PageVo(IPage pageModel) {
        this.setRecords(pageModel.getRecords());
        this.setTotal(pageModel.getTotal());
        this.setSize(pageModel.getSize());
        this.setPages(pageModel.getPages());
        this.setCurrent(pageModel.getCurrent());
    }

    public PageVo(List<T> list, IPage pageModel) {
        this.setRecords(list);
        this.setTotal(pageModel.getTotal());
        this.setSize(pageModel.getSize());
        this.setPages(pageModel.getPages());
        this.setCurrent(pageModel.getCurrent());
    }

}
