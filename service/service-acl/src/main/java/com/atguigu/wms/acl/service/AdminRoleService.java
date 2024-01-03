package com.atguigu.wms.acl.service;


import com.atguigu.wms.model.acl.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色服务接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
public interface AdminRoleService extends IService<AdminRole> {

    String getRoleNameByAdminId(Long adminId);
}
