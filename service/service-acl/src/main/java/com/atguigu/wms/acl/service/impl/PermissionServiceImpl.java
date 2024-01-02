package com.atguigu.wms.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.model.acl.Permission;
import com.atguigu.wms.model.acl.RolePermission;
import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.acl.helper.MemuHelper;
import com.atguigu.wms.acl.helper.PermissionHelper;
import com.atguigu.wms.acl.mapper.PermissionMapper;
import com.atguigu.wms.acl.mapper.RolePermissionMapper;
import com.atguigu.wms.acl.mapper.AdminMapper;
import com.atguigu.wms.acl.service.PermissionService;
import com.atguigu.wms.acl.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限服务实现类
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private AdminMapper userMapper;

	/**
	 * 获取全部菜单
	 * @return
	 */
	public List<Permission> queryAllMenu() {
		//获取全部权限数据
		List<Permission> allPermissionList = permissionMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

		//把权限数据构建成树形结构数据
		List<Permission> result = PermissionHelper.bulid(allPermissionList);
		return result;
	}

	/**
	 *	获取用户菜单
	 * @return
	 */
	public List<Permission> selectAllMenu(Long roleId) {
		List<Permission> allPermissionList = permissionMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

		//根据角色id获取角色权限
		List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id",roleId));
		//转换给角色id与角色权限对应Map对象
		List<Long> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
		allPermissionList.forEach(permission -> {
			if(permissionIdList.contains(permission.getId())) {
				permission.setSelect(true);
			} else {
				permission.setSelect(false);
			}
		});

		List<Permission> permissionList = PermissionHelper.bulid(allPermissionList);
		return permissionList;
	}

	/**
	 * 保存角色权限
	 * @param roleId
	 * @param permissionIds
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {
		rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", roleId));
//		rolePermissionMapper.insertRolePermissionRealtion(roleId,permissionId);

		List<RolePermission> rolePermissionList = new ArrayList<>();
		for(Long permissionId : permissionIds) {
			if(StringUtils.isEmpty(permissionId)) continue;
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(roleId);
			rolePermission.setPermissionId(permissionId);
			rolePermissionList.add(rolePermission);
		}
		rolePermissionService.saveBatch(rolePermissionList);
	}

	/**
	 * 递归删除
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeChildById(Long id) {
		List<Long> idList = new ArrayList<>();
		this.selectChildListById(id, idList);

		idList.add(id);
		permissionMapper.deleteBatchIds(idList);
		return true;
	}

	/**
	 *	递归获取子节点
	 * @param id
	 * @param idList
	 */
	private void selectChildListById(Long id, List<Long> idList) {
		List<Permission> childList = permissionMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
		childList.stream().forEach(item -> {
			idList.add(item.getId());
			this.selectChildListById(item.getId(), idList);
		});
	}

	/**
	 * 根据用户id获取用户菜单权限
	 * @param adminId
	 * @return
	 */
	@Override
	public List<JSONObject> selectPermissionByUserId(Long adminId) {
		List<Permission> selectPermissionList = null;
		if(this.isSysAdmin(adminId)) {
			//如果是超级管理员，获取所有菜单
			selectPermissionList = permissionMapper.selectList(null);
		} else {
			selectPermissionList = permissionMapper.selectPermissionByUserId(adminId);
		}

		List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
		List<JSONObject> result = MemuHelper.bulid(permissionList);
		return result;
	}

	/**
	 * 根据用户id获取操作权限值
	 * @param adminId
	 * @return
	 */
	@Override
	public List<String> selectPermissionValueByUserId(Long adminId) {
		List<String> selectPermissionValueList = null;
		if(this.isSysAdmin(adminId)) {
			//如果是系统管理员，获取所有权限
			selectPermissionValueList = permissionMapper.selectAllPermissionValue();
		} else {
			selectPermissionValueList = permissionMapper.selectPermissionValueByUserId(adminId);
		}
		return selectPermissionValueList;
	}

	/**
	 * 判断用户是否系统管理员
	 * @param adminId
	 * @return
	 */
	private boolean isSysAdmin(Long adminId) {
		Admin user = userMapper.selectById(adminId);
		if(null != user && "admin".equals(user.getUsername())) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> selectMenuByUserId(Long adminId) {
		List<Permission> selectPermissionList = null;
		if(this.isSysAdmin(adminId)) {
			//如果是超级管理员，获取所有菜单
			selectPermissionList = permissionMapper.selectList(null);
		} else {
			selectPermissionList = permissionMapper.selectPermissionByUserId(adminId);
		}

		List<String> list = new ArrayList<>();
		for(Permission permission : selectPermissionList) {
			if(permission.getType().intValue() == 1) {
				if(!StringUtils.isEmpty(permission.getCode())) {
					list.add(permission.getCode());
				}
			} else {
				if(!StringUtils.isEmpty(permission.getToCode())) {
					list.add(permission.getToCode());
				}
			}

		}
		return list;
	}
}
