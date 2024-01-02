package com.atguigu.wms.common.security;

import com.atguigu.wms.vo.acl.AdminLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    public AdminLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.initUserLoginVo(request);
        return true;
    }

    private void initUserLoginVo(HttpServletRequest request){
        String userId = request.getHeader("userId");
        String userName = request.getHeader("userName");
        String warehouseId = request.getHeader("warehouseId");
        if (!StringUtils.isEmpty(userId)) {
            AuthContextHolder.setUserId(Long.parseLong(userId));
            AuthContextHolder.setUserName(userName);
            Long warehouseId1 = StringUtils.isEmpty(warehouseId) ? null : Long.parseLong(warehouseId);
            AuthContextHolder.setWarehouseId(warehouseId1);
        }
    }

}
