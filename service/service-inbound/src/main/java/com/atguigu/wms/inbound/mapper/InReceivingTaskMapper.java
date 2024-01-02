package com.atguigu.wms.inbound.mapper;

import com.atguigu.wms.model.inbound.InReceivingTask;
import com.atguigu.wms.vo.inbound.InReceivingTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InReceivingTaskMapper extends BaseMapper<InReceivingTask> {

    IPage<InReceivingTask> selectPage(Page<InReceivingTask> page, @Param("vo") InReceivingTaskQueryVo inReceivingTaskQueryVo);

}
