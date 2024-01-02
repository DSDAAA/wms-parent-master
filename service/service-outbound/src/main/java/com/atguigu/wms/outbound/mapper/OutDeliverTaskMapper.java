package com.atguigu.wms.outbound.mapper;

import com.atguigu.wms.model.outbound.OutDeliverTask;
import com.atguigu.wms.vo.outbound.OutDeliverTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OutDeliverTaskMapper extends BaseMapper<OutDeliverTask> {

    IPage<OutDeliverTask> selectPage(Page<OutDeliverTask> page, @Param("vo") OutDeliverTaskQueryVo outDeliverTaskQueryVo);

}
