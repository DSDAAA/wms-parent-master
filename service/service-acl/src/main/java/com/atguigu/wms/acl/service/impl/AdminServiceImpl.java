package com.atguigu.wms.acl.service.impl;

import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.acl.AdminQueryVo;
import com.atguigu.wms.acl.mapper.AdminMapper;
import com.atguigu.wms.acl.service.AdminRoleService;
import com.atguigu.wms.acl.service.AdminService;
import com.atguigu.wms.acl.service.RoleService;
import com.atguigu.wms.vo.acl.AdminLoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

	@Autowired
	private AdminMapper userMapper;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AdminRoleService userRoleService;

	@Autowired
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Override
	public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo userQueryVo) {
		IPage<Admin> page = userMapper.selectPage(pageParam, userQueryVo);
		page.getRecords().stream().forEach(item -> {
			item.setRoleName(userRoleService.getRoleNameByAdminId(item.getId()));
			if(null != item.getWarehouseId()) {
				item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
			}
		});
		return page;
	}

	@Override
	public Admin selectByUsername(String username) {
		return userMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
	}

    @Override
    public AdminLoginVo getAdminLoginVo(Long adminId) {
		AdminLoginVo adminLoginVo = new AdminLoginVo();
		Admin admin = this.getById(adminId);
		adminLoginVo.setAdminId(admin.getId());
		adminLoginVo.setName(admin.getName());
		adminLoginVo.setWarehouseId(admin.getWarehouseId());
        return adminLoginVo;
    }

    @Override
    public List<Admin> findByKeyword(String keyword) {
		LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.like(Admin::getName, keyword);
		return this.list(queryWrapper);
    }

	@Override
	public void doAssignWarehouse(Long adminId, Long warehouseId) {
		Admin admin = this.getById(adminId);
		admin.setWarehouseId(warehouseId);
		this.updateById(admin);
	}
}
