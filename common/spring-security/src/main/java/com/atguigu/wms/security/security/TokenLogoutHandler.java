package com.atguigu.wms.security.security;

import com.atguigu.wms.common.helper.JwtHelper;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登出业务逻辑类
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            //清空当前用户缓存中的权限数据
            String userName = JwtHelper.getUserName(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, Result.ok());
    }

}
