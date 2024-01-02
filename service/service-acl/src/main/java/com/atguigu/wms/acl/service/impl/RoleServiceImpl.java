package com.atguigu.wms.acl.service.impl;

import com.atguigu.wms.acl.service.AdminRoleService;
import com.atguigu.wms.acl.service.RoleService;
import com.atguigu.wms.model.acl.Role;
import com.atguigu.wms.model.acl.AdminRole;
import com.atguigu.wms.vo.acl.RoleQueryVo;
import com.atguigu.wms.acl.mapper.RoleMapper;
import com.atguigu.wms.acl.mapper.AdminRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色服务实现类
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private AdminRoleMapper userRoleMapper;

	@Autowired
	private AdminRoleService adminRoleService;

	@Override
	public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {

		return roleMapper.selectPage(pageParam, roleQueryVo);
	}

	/**
	 * 分配角色
	 * @param adminId
	 * @param roleIds
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
		userRoleMapper.delete(new QueryWrapper<AdminRole>().eq("admin_id", adminId));

		List<AdminRole> userRoleList = new ArrayList<>();
		for(Long roleId : roleIds) {
			if(StringUtils.isEmpty(roleId)) continue;
			AdminRole userRole = new AdminRole();
			userRole.setAdminId(adminId);
			userRole.setRoleId(roleId);
			userRoleList.add(userRole);
		}
		adminRoleService.saveBatch(userRoleList);
	}

	/**
	 * 根据用户获取角色数据
	 * @param adminId
	 * @return
	 */
	@Override
	public Map<String, Object> findRoleByUserId(Long adminId) {
		//查询所有的角色
		List<Role> allRolesList =roleMapper.selectList(null);

		//拥有的角色id
		List<AdminRole> existUserRoleList = userRoleMapper.selectList(new QueryWrapper<AdminRole>().eq("admin_id", adminId).select("role_id"));
		List<Long> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

		//对角色进行分类
		List<Role> assignRoles = new ArrayList<Role>();
		for (Role role : allRolesList) {
			//已分配
			if(existRoleList.contains(role.getId())) {
				assignRoles.add(role);
			}
		}

		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("assignRoles", assignRoles);
		roleMap.put("allRolesList", allRolesList);
		return roleMap;
	}

	/**
	 * 根据用户id获取角色列表
	 * @param adminId
	 * @return
	 */
	public List<Role> selectRoleByUserId(Long adminId) {
		//根据用户id拥有的角色id
		List<AdminRole> adminRoleList = userRoleMapper.selectList(new QueryWrapper<AdminRole>().eq("admin_id", adminId).select("role_id"));
		List<Long> roleIdList = adminRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
		List<Role> roleList = new ArrayList<>();
		if(roleIdList.size() > 0) {
			roleList = roleMapper.selectBatchIds(roleIdList);
		}
		return roleList;
	}

}
