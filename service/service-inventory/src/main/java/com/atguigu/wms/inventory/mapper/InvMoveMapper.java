package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InvMove;
import com.atguigu.wms.vo.inventory.InvMoveQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvMoveMapper extends BaseMapper<InvMove> {

    IPage<InvMove> selectPage(Page<InvMove> page, @Param("vo") InvMoveQueryVo invMoveQueryVo);

}
