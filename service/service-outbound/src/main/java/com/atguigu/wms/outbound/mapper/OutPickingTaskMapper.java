package com.atguigu.wms.outbound.mapper;

import com.atguigu.wms.model.outbound.OutPickingTask;
import com.atguigu.wms.vo.outbound.OutPickingTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OutPickingTaskMapper extends BaseMapper<OutPickingTask> {

    IPage<OutPickingTask> selectPage(Page<OutPickingTask> page, @Param("vo") OutPickingTaskQueryVo outPickingTaskQueryVo);

}
