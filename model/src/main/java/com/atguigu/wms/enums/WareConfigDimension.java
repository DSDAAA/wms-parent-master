package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum WareConfigDimension {
    //1：货架 2：库位 3：商品
    STORESHELF(1,"货架"),
    STOREHOUSE(2,"库位"),
    GOODS(3,"商品"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    WareConfigDimension(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}