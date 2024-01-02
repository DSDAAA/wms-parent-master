package com.atguigu.wms.common.security;

/**
 * 获取登录用户信息类
 *
 */
public class AuthContextHolder {

    //后台用户id
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();
    //后台用户姓名
    private static ThreadLocal<String> userName = new ThreadLocal<String>();
    private static ThreadLocal<Long> warehouseId = new ThreadLocal<Long>();

    public static Long getUserId() {
        return userId.get();
    }

    public static void setUserId(Long _userId) {
        userId.set(_userId);
    }

    public static String getUserName(){
        return userName.get();
    }

    public static void setUserName(String _userName){
        userName.set(_userName);
    }

    public static Long getWarehouseId() {
        return warehouseId.get();
    }

    public static void setWarehouseId(Long _warehouseId) {
        warehouseId.set(_warehouseId);
    }

}
