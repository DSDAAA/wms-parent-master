package com.atguigu.wms.acl.mapper;

import com.atguigu.wms.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色权限Mpper接口
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Repository
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    //void insertRolePermissionRealtion(@Param("roleId") String adminId, @Param("permissionIds") String[] permissionId);
}
