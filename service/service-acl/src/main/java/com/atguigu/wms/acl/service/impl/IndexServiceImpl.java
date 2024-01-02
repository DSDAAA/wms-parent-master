package com.atguigu.wms.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.acl.service.AdminService;
import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.acl.service.RoleService;
import com.atguigu.wms.model.acl.Role;
import com.atguigu.wms.acl.service.IndexService;
import com.atguigu.wms.acl.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        Admin admin = adminService.selectByUsername(username);
        if (null == admin) {
            throw new WmsException(ResultCodeEnum.SERVICE_ERROR);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(admin.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(admin.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        ////根据用户id获取菜单权限值
        List<String> menuList = permissionService.selectMenuByUserId(admin.getId());

//        result.put("name", admin.getName());
//        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//        result.put("roles", roleNameList);
//        result.put("permissionValueList", permissionValueList);

        result.put("name", admin.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles",  new HashSet<>(roleNameList));
        result.put("routes", new HashSet<>(menuList));
        result.put("buttons", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
//    public List<JSONObject> getMenu(String username) {
//        Admin admin = adminService.selectByUsername(username);
//
//        //根据用户id获取用户菜单权限
//        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(admin.getId());
//        return permissionList;
//    }

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    public Map<String, Object> getMenu(String username) {
        Admin admin = adminService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        List<JSONObject> menuList = permissionService.selectPermissionByUserId(admin.getId());
        List<String> permissionList = permissionService.selectPermissionValueByUserId(admin.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", menuList);
        map.put("permissionList", permissionList);
        return map;
    }


}
