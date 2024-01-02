package com.atguigu.wms.common.constant;

public class MqConst {

    /**
     * 库存
     */
    public static final String EXCHANGE_INVENTORY = "wms.inventory";
    public static final String ROUTING_UNLOCK = "wms.unlock";
    public static final String ROUTING_MINUS = "wms.minus";
    public static final String ROUTING_DELIVER = "wms.deliver";
    public static final String ROUTING_OUTBOUND = "wms.outbound";
    public static final String ROUTING_PICKING = "wms.picking";
    //队列
    public static final String QUEUE_UNLOCK  = "wms.unlock";
    public static final String QUEUE_MINUS  = "wms.minus";
    public static final String QUEUE_DELIVER  = "wms.deliver";
    public static final String QUEUE_OUTBOUND  = "wms.outbound";
    public static final String QUEUE_PICKING  = "wms.picking";

//    /**
//     * 取消订单，发送延迟队列
//     */
//    public static final String EXCHANGE_INVENTORY_DELAY = "wms.inventory.delay";//"exchange.direct.order.create" test_exchange;
//    public static final String ROUTING_UNLOCK_DELAY = "wms.unlock.delay";
//    //延迟取消订单队列
//    public static final String QUEUE_UNLOCK_DELAY  = "wms.unlock.delay";
//    //取消订单 延迟时间 单位：秒
//    public static final int DELAY_TIME  = 30*60;

}
