package com.atguigu.wms.outbound.client.impl;


import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.outbound.client.OutboundFeignClient;
import org.springframework.stereotype.Component;

@Component
public class OutboundDegradeFeignClient implements OutboundFeignClient {


    @Override
    public OutOrder getByOutOrderNo(String outOrderNo) {
        return null;
    }

    @Override
    public Boolean updateFinishStatus(Long id) {
        return false;
    }
}
