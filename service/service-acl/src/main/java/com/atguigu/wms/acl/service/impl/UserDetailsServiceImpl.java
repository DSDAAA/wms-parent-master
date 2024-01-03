package com.atguigu.wms.acl.service.impl;

import com.atguigu.wms.acl.service.AdminService;
import com.atguigu.wms.acl.service.PermissionService;
import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.security.entity.SecurityUser;
import com.atguigu.wms.security.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        Admin admin = adminService.selectByUsername(username);

        // 判断用户是否存在
        if (null == admin){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        User curUser = new User();
        BeanUtils.copyProperties(admin,curUser);

        List<String> authorities = permissionService.selectPermissionValueByUserId(admin.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
