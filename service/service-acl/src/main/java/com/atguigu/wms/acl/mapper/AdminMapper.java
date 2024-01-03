package com.atguigu.wms.acl.mapper;

import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户Mpper接口
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@Repository
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    IPage<Admin> selectPage(Page<Admin> page, @Param("vo") AdminQueryVo userQueryVo);
}
