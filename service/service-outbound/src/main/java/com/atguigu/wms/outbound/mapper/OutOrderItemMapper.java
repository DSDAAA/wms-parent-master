package com.atguigu.wms.outbound.mapper;

import com.atguigu.wms.model.outbound.OutOrderItem;
import com.atguigu.wms.vo.outbound.OutOrderItemQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OutOrderItemMapper extends BaseMapper<OutOrderItem> {

    IPage<OutOrderItem> selectPage(Page<OutOrderItem> page, @Param("vo") OutOrderItemQueryVo outOrderItemQueryVo);

}
