package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum InOrderStatus {
    //订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】
    CREATE(0,"新建"),
    APPROVEL_RUN(1,"审批中"),
    RECEIVING_RUN(2,"收货中"),
    PUTAWAY_RUN(3,"上架中"),
    //INVENTORY_RUN(3,"库存同步中"),
    FINISH(4,"完成"),
    REJECT(-1,"驳回"),
    ;

    @EnumValue
    private Integer code ;
    private String comment ;

    InOrderStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}