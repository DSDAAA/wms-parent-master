package com.atguigu.wms.inbound.mapper;

import com.atguigu.wms.model.inbound.InApproveTask;
import com.atguigu.wms.vo.inbound.InApproveTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InApproveTaskMapper extends BaseMapper<InApproveTask> {

    IPage<InApproveTask> selectPage(Page<InApproveTask> page, @Param("vo") InApproveTaskQueryVo inApproveTaskQueryVo);

}
