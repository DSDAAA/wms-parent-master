package com.atguigu.wms.acl.mapper;

import com.atguigu.wms.model.acl.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限Mpper接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@Repository
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 获取用户菜单权限
     * @param adminId
     * @return
     */
    List<Permission> selectPermissionByUserId(@Param("adminId") Long adminId);

    /**
     * 获取用户操作权限
     * @param adminId
     * @return
     */
    List<String> selectPermissionValueByUserId(@Param("adminId") Long adminId);

    /**
     * 获取全部操作权限
     * @return
     */
    List<String> selectAllPermissionValue();
}
