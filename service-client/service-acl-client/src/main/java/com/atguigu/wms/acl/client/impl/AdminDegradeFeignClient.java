package com.atguigu.wms.acl.client.impl;


import com.atguigu.wms.acl.client.AdminFeignClient;
import org.springframework.stereotype.Component;

@Component
public class AdminDegradeFeignClient implements AdminFeignClient {


    @Override
    public String getNameById(Long id) {
        return "";
    }
}
