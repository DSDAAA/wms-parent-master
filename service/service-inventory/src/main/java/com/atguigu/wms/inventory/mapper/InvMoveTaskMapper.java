package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InvMoveTask;
import com.atguigu.wms.vo.inventory.InvMoveTaskQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvMoveTaskMapper extends BaseMapper<InvMoveTask> {

    IPage<InvMoveTask> selectPage(Page<InvMoveTask> page, @Param("vo") InvMoveTaskQueryVo invMoveTaskQueryVo);

}
