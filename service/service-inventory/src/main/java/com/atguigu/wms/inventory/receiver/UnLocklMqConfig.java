package com.atguigu.wms.inventory.receiver;

import com.atguigu.wms.common.constant.MqConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UnLocklMqConfig {

//    @Bean
//    public Queue delayQueue() {
//        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
//        return new Queue(MqConst.QUEUE_UNLOCK_DELAY, true);
//    }
//
//    @Bean
//    public CustomExchange delayExchange() {
//        Map<String, Object> args = new HashMap<String, Object>();
//        args.put("x-delayed-type", "direct");
//        return new CustomExchange(MqConst.EXCHANGE_INVENTORY_DELAY, "x-delayed-message", true, false, args);
//    }
//
//    @Bean
//    public Binding bindingDelay() {
//        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(MqConst.ROUTING_UNLOCK_DELAY).noargs();
//    }

}
