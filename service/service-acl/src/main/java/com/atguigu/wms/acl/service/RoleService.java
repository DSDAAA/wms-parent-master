package com.atguigu.wms.acl.service;


import com.atguigu.wms.model.acl.Role;
import com.atguigu.wms.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色服务接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
public interface RoleService extends IService<Role> {

	/**
	 * 讲师分页列表
	 * @param roleQueryVo
	 * @return
	 */
	IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

	/**
	 * 分配角色
	 * @param adminId
	 * @param roleIds
	 */
	void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);

	/**
	 * 根据用户获取角色数据
	 * @param adminId
	 * @return
	 */
	Map<String, Object> findRoleByUserId(Long adminId);

	/**
	 * 根据用户id获取角色列表
	 * @param adminId
	 * @return
	 */
	List<Role> selectRoleByUserId(Long adminId);
}
