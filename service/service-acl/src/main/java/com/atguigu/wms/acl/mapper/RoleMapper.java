package com.atguigu.wms.acl.mapper;

import com.atguigu.wms.model.acl.Role;
import com.atguigu.wms.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色Mpper接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> selectPage(Page<Role> page, @Param("vo") RoleQueryVo roleQueryVo);
}
