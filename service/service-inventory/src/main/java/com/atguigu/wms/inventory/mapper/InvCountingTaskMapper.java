package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InvCountingTask;
import com.atguigu.wms.vo.inventory.InvCountingTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvCountingTaskMapper extends BaseMapper<InvCountingTask> {

    IPage<InvCountingTask> selectPage(Page<InvCountingTask> page, @Param("vo") InvCountingTaskQueryVo invCountingTaskQueryVo);

}
