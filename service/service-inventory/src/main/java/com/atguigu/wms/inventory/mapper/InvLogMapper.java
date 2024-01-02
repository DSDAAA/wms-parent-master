package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InvLog;
import com.atguigu.wms.vo.inventory.InvLogQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvLogMapper extends BaseMapper<InvLog> {

    IPage<InvLog> selectPage(Page<InvLog> page, @Param("vo") InvLogQueryVo invLogQueryVo);

}
