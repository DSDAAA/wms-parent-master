package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OutDeliverTaskStatus {

    PENDING_DELIVER(0,"待发货"),
    FINISH(1,"完成"),
    ;


    @EnumValue
    private Integer code ;
    private String comment ;

    OutDeliverTaskStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}