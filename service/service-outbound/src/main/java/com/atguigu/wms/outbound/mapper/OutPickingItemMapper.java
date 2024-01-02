package com.atguigu.wms.outbound.mapper;

import com.atguigu.wms.model.outbound.OutPickingItem;
import com.atguigu.wms.vo.outbound.OutPickingItemQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OutPickingItemMapper extends BaseMapper<OutPickingItem> {

    IPage<OutPickingItem> selectPage(Page<OutPickingItem> page, @Param("vo") OutPickingItemQueryVo outPickingItemQueryVo);

}
