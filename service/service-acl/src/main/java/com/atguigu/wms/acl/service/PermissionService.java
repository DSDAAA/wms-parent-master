package com.atguigu.wms.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限服务接口
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public interface PermissionService extends IService<Permission> {


	List<Permission> queryAllMenu();

	/**
	 *	获取用户菜单
	 * @return
	 */
	List<Permission> selectAllMenu(Long roleId);

	/**
	 * 保存角色权限
	 * @param roleId
	 * @param permissionIds
	 */
	void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds);

	/**
	 * 递归删除
	 * @param id
	 * @return
	 */
	boolean removeChildById(Long id);

	/**
	 * 根据用户id获取用户菜单权限
	 * @param adminId
	 * @return
	 */
	List<JSONObject> selectPermissionByUserId(Long adminId);

	/**
	 * 根据用户id获取操作权限值
	 * @param adminId
	 * @return
	 */
	List<String> selectPermissionValueByUserId(Long adminId);

	List<String> selectMenuByUserId(Long adminId);
}
