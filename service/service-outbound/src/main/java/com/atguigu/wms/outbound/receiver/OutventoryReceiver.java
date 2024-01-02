package com.atguigu.wms.outbound.receiver;

import com.alibaba.fastjson.JSON;
import com.atguigu.wms.common.constant.MqConst;
import com.atguigu.wms.common.constant.RedisConst;
import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.model.outbound.OutOrderItem;
import com.atguigu.wms.outbound.service.OutOrderService;
import com.atguigu.wms.vo.outbound.OrderItemLockVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import com.atguigu.wms.vo.outbound.OutOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OutventoryReceiver {


    @Resource
    private OutOrderService outOrderService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 订单出库
     * @param content：原订单被拆单，则多个分订单一起发送过来
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_OUTBOUND, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_INVENTORY),
            key = {MqConst.ROUTING_OUTBOUND}
    ))
    public void orderOutbound(String content) {
        try {
            System.out.println(content);
            List<OutOrder> outOrderList = this.packageOutOrder(content);
            if(outOrderList.size() > 0) {
                outOrderService.saveOutOrderList(outOrderList);

                // 生成出库单之后，删除锁定库存的缓存。以防止重复解锁库存
                OutOrder outOrder = outOrderList.get(0);
                String orderNo = StringUtils.isEmpty(outOrder.getParentOrderNo()) ? outOrder.getOrderNo() : outOrder.getParentOrderNo();
                this.redisTemplate.delete(RedisConst.INVENTORY_INFO_PREFIX + orderNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<OutOrder> packageOutOrder(String content) {
        List<OutOrder> outOrderList = new ArrayList<>();
        int jsonType = this.getJSONType(content);
        if(jsonType == 1) {
            OutOrderVo outOrderVo = JSON.parseObject(content, OutOrderVo.class);
            outOrderList.add(this.packageOutOrder(outOrderVo));
        } else {
            List<OutOrderVo> outOrderVoList = JSON.parseArray(content, OutOrderVo.class);
            outOrderVoList.forEach(outOrderVo -> {
                outOrderList.add(this.packageOutOrder(outOrderVo));
            });
        }
        return outOrderList;
    }

    private OutOrder packageOutOrder(OutOrderVo outOrderVo) {
        OutOrder outOrder = new OutOrder();
        BeanUtils.copyProperties(outOrderVo, outOrder);
        List<OutOrderItem> outOrderItemList = outOrderVo.getOutOrderItemVoList().stream().map(item -> {
            OutOrderItem outOrderItem = new OutOrderItem();
            outOrderItem.setSkuId(item.getSkuId());
            outOrderItem.setBuyCount(item.getCount());
            return outOrderItem;
        }).collect(Collectors.toList());
        outOrder.setOutOrderItemList(outOrderItemList);
        return outOrder;
    }

    /**
     * 简单判断json数据格式
     * @param content
     * @return 1：对象 2：数组
     */
    private int getJSONType(String content) {
        int result = 1;
        if (StringUtils.isNotBlank(content)) {
            content = content.trim();
            if (content.startsWith("{") && content.endsWith("}")) {
                result = 1;
            } else if (content.startsWith("[") && content.endsWith("]")) {
                result = 2;
            }
        }
        return result;
    }
}
