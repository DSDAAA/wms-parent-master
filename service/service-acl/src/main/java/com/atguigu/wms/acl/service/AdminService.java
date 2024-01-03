package com.atguigu.wms.acl.service;

import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.acl.AdminQueryVo;
import com.atguigu.wms.vo.acl.AdminLoginVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户服务接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
public interface AdminService extends IService<Admin> {

	/**
	 * 用户分页列表
	 * @param pageParam
	 * @param userQueryVo
	 * @return
	 */
	IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo userQueryVo);

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	Admin selectByUsername(String username);

	/**
	 * 获取当前登录用户信息
	 * @param adminId
	 * @return
	 */
	AdminLoginVo getAdminLoginVo(Long adminId);

	List<Admin> findByKeyword(String keyword);

    void doAssignWarehouse(Long adminId, Long warehouseId);
}
