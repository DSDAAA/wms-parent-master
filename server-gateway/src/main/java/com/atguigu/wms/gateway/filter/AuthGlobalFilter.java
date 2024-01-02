package com.atguigu.wms.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.common.helper.JwtHelper;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 全局Filter，统一处理用户登录与外部不允许访问的服务
 * </p>
 *
 * @author qy
 * @since 2019-11-21
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    //web服务配置必须登录的url
    private static String[] authUrls = {"/admin/acl/index/login"};

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("path {}", path);

        //内部服务接口，不允许外部访问
        if(antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            return out(response, ResultCodeEnum.PERMISSION);
        }

        if(antPathMatcher.match("/api/**", path)) {
            return chain.filter(exchange);
        }

        //不需要登录
        for(String url : authUrls) {
            if (path.indexOf(url) != -1) {
                return chain.filter(exchange);
            }
        }

        //api接口，异步请求，校验用户必须登录
        Long userId = this.getUserId(request);
        log.info("userId {}", userId);
        if(StringUtils.isEmpty(userId)) {
            ServerHttpResponse response = exchange.getResponse();
            return out(response, ResultCodeEnum.LOGIN_AUTH);
        }

        if(!StringUtils.isEmpty(userId)) {
            String userName = this.getUserName(request);
            Long warehouseId = this.getWarehouseId(request);
            request.mutate().header("userId", String.valueOf(userId)).build();
            request.mutate().header("userName", userName).build();
            if(!StringUtils.isEmpty(warehouseId)) {
                request.mutate().header("warehouseId", String.valueOf(warehouseId)).build();
            }
            //将现在的request 变成 exchange对象
            return chain.filter(exchange.mutate().request(request).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * api接口鉴权失败返回数据
     * @param response
     * @return
     */
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 获取当前登录用户id
     * @param request
     * @return
     */
    private Long getUserId(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }
        log.info("token {}", token);
        if(!StringUtils.isEmpty(token)) {
            Long userId = JwtHelper.getUserId(token);
            return userId;
        }
        return null;
    }

    /**
     * 获取当前登录用户id
     * @param request
     * @return
     */
    private String getUserName(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }
        log.info("token {}", token);
        if(!StringUtils.isEmpty(token)) {
            String userName = JwtHelper.getUserName(token);
            return userName;
        }
        return null;
    }

    private Long getWarehouseId(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }
        log.info("token {}", token);
        if(!StringUtils.isEmpty(token)) {
            Long warehouseId = JwtHelper.getWarehouseId(token);
            return warehouseId;
        }
        return null;
    }
}
