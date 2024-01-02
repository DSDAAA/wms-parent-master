package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InvStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvStatisticsMapper extends BaseMapper<InvStatistics> {


}
