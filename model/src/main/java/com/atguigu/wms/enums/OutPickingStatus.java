package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OutPickingStatus {
    //
    PENDING_PICKING(0,"待拣货"),
    FINISH(1,"完成"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    OutPickingStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}