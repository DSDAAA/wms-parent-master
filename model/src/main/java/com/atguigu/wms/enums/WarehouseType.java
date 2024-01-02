package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum WarehouseType {
    //1：中心仓库 2：区域仓库
    CENTRE(1,"中心仓库"),
    CITY(2,"区域仓库"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    WarehouseType(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}