package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InvMoveItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvMoveItemMapper extends BaseMapper<InvMoveItem> {


}
