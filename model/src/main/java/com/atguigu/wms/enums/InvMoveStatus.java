package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum InvMoveStatus {
    //订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】
    CREATE(0,"新建"),
    PENDING_COUNTING(1,"待转移"),
    //PENDING_SYNC(2,"待同步库存"),
    FINISH(2,"完成"),
    //REJECT(-1,"驳回"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    InvMoveStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}