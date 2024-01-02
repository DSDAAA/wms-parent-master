package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum InTaskStatus {
    //订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】
    PENDING_APPROVEL(0,"待处理"),
    FINISH(1,"完成"),
    REJECT(-1,"未到货"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    InTaskStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}