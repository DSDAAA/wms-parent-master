package com.atguigu.wms.acl.mapper;

import com.atguigu.wms.model.acl.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户角色Mpper接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@Repository
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

}
