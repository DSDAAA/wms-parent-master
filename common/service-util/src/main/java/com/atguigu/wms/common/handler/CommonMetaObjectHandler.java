package com.atguigu.wms.common.handler;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("createId", AuthContextHolder.getUserId(), metaObject);
        this.setFieldValByName("createName", AuthContextHolder.getUserName(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateId", AuthContextHolder.getUserId(), metaObject);
        this.setFieldValByName("updateName", AuthContextHolder.getUserName(), metaObject);
    }
}