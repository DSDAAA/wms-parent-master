package com.atguigu.wms.inbound.mapper;

import com.atguigu.wms.model.inbound.InPutawayTask;
import com.atguigu.wms.vo.inbound.InPutawayTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InPutawayTaskMapper extends BaseMapper<InPutawayTask> {

    IPage<InPutawayTask> selectPage(Page<InPutawayTask> page, @Param("vo") InPutawayTaskQueryVo inPutawayTaskQueryVo);

}
