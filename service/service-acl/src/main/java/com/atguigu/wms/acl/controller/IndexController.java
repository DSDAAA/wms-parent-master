package com.atguigu.wms.acl.controller;

import com.atguigu.wms.common.constant.RedisConst;
import com.atguigu.wms.common.helper.JwtHelper;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.util.MD5;
import com.atguigu.wms.acl.service.IndexService;
import com.atguigu.wms.acl.service.AdminService;
import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.vo.acl.AdminLoginVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台登录与权限管理 前端控制器
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@RestController
@RequestMapping("/admin/acl/index")
@Api(tags = "后台登录与权限管理")
//@CrossOrigin //跨域
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 1、请求登陆的login
     */
    @PostMapping("login")
    public Result login(@RequestBody Admin adminVo, HttpServletRequest request, HttpServletResponse response) {
        Admin admin = adminService.selectByUsername(adminVo.getUsername());

        if(!MD5.encrypt(adminVo.getPassword()).equals(admin.getPassword())) {
            return Result.fail("密码错误");
        }
        String token = JwtHelper.createToken(admin.getId(), admin.getUsername(), admin.getWarehouseId());
        return Result.ok(token);
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request){
        //获取当前登录用户用户名
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String token = request.getHeader("token");
        String username = JwtHelper.getUserName(token);//tokenManager.getUserFromToken(token);

        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.ok(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public Result getMenu(HttpServletRequest request){
        //获取当前登录用户用户名
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String token = request.getHeader("token");
        String username = JwtHelper.getUserName(token);//tokenManager.getUserFromToken(token);
        Map<String, Object> menuMap = indexService.getMenu(username);
        return Result.ok(menuMap);
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }

}

