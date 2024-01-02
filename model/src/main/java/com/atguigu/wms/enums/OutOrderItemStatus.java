package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OutOrderItemStatus {
    //订单状态0：待拣货，1：拣货中 2：待发货 3：已发货
    PENDING_PICKING(0,"待拣货"),
    ASSIGN(1,"已分配"),
    PICKING(2,"已拣货"),
    FINISH(3,"完成");
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    OutOrderItemStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}