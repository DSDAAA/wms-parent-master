package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum InvLogType {
    //订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】
    PUTAWAY(1,"上架"),
    INV_COUNTING(2,"盘点"),
    OUT_LOCK(3,"锁定库存"),
    OUT_UNLOCK(4,"解锁库存"),
    OUT_MINUS(5,"出库减库存"),
    HAND_PUTAWAY(6,"手动上架"),
    INV_MOVE(7,"移库"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    InvLogType(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}