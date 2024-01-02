package com.atguigu.wms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OutOrderStatus {
    //订单状态0：待拣货，1：拣货中 2：待发货 3：已发货
    //UNPAID(0,"未支付"),
//    PAID(1,"已支付" ),
//    OUT_OF_STOCK(2,"已付款，库存超卖"),
    PENDING_PICKING(0,"待拣货"),
    PICKING_RUN(1,"拣货中"),
    PENDING_DELIVER(2,"待发货"),
    PUTAWAY_RUN(3,"已发货"),
    FINISH(4,"完成"),
//    REJECT(-1,"驳回"),
//    SPLIT(-2,"已拆分"),
    ;


    @EnumValue
    private Integer code ;
    private String comment ;

    OutOrderStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}