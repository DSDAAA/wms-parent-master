package com.atguigu.wms.acl.service.impl;

import com.atguigu.wms.model.acl.AdminRole;
import com.atguigu.wms.model.acl.Role;
import com.atguigu.wms.acl.mapper.AdminRoleMapper;
import com.atguigu.wms.acl.service.AdminRoleService;
import com.atguigu.wms.acl.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 用户角色服务实现类
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Autowired
	private RoleService roleService;

	@Override
	public String getRoleNameByAdminId(Long adminId) {
		List<AdminRole> userRoleList = adminRoleMapper.selectList(new QueryWrapper<AdminRole>().eq("admin_id", adminId));
		if(!CollectionUtils.isEmpty(userRoleList)) {
			String roleName = "";
			for(AdminRole userRole : userRoleList) {
				Role role = roleService.getById(userRole.getRoleId());
				if(null != role) {
					roleName += role.getRoleName()+",";
				}
			}
			if(roleName.length() > 0) {
				roleName = roleName.substring(0, roleName.length()-1);
			}

			return roleName;
		}
		return "";
	}

}
