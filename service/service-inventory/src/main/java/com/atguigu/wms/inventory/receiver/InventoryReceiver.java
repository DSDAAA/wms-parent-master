package com.atguigu.wms.inventory.receiver;

import com.alibaba.fastjson.JSON;
import com.atguigu.wms.common.constant.MqConst;
import com.atguigu.wms.common.service.RabbitService;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.model.outbound.OutPickingItem;
import com.atguigu.wms.vo.outbound.OutOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class InventoryReceiver {

    @Resource
    private InventoryInfoService inventoryInfoService;

    @Resource
    private RabbitService rabbitService;

    /**
     * 解锁库存
     * @param orderNo 订单号
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_UNLOCK, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_INVENTORY),
            key = {MqConst.ROUTING_UNLOCK}
    ))
    public void unlock(String orderNo) {
        if (StringUtils.isEmpty(orderNo)){
            return ;
        }

        inventoryInfoService.unlock(orderNo);
    }

    /**
     * 减库存
     * @param outOrderNo  出库单
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MINUS, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_INVENTORY),
            key = {MqConst.ROUTING_MINUS}
    ))
    public void minus(String outOrderNo) {
        if (StringUtils.isEmpty(outOrderNo)){
            return ;
        }

        //扣减库存
        inventoryInfoService.minus(outOrderNo);
    }


//    /**
//     * 延迟解锁，锁定库存{30}分钟后未提交出库单（即未支付），自动解锁库存
//     * 延迟队列，不能再这里做交换机与队列绑定
//     * @param orderNo
//     */
//    @RabbitListener(queues = MqConst.QUEUE_UNLOCK_DELAY)
//    public void delayUnlock(String orderNo) {
//        if (StringUtils.isEmpty(orderNo)) {
//           return;
//        }
//        inventoryInfoService.unlock(orderNo);
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_PICKING, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_INVENTORY),
            key = {MqConst.ROUTING_PICKING}
    ))
    public void picking(String content) {
        System.out.println(content);
        List<OutPickingItem> outPickingItemList = JSON.parseArray(content, OutPickingItem.class);
        if(!CollectionUtils.isEmpty(outPickingItemList)) {
            inventoryInfoService.picking(outPickingItemList);
        }
    }
}
