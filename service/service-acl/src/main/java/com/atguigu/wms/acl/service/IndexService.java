package com.atguigu.wms.acl.service;

import java.util.Map;

public interface IndexService {

    /**
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    //List<JSONObject> getMenu(String username);
    Map<String, Object> getMenu(String username);

}
