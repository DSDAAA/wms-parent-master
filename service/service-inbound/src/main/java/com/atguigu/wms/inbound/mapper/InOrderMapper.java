package com.atguigu.wms.inbound.mapper;

import com.atguigu.wms.model.inbound.InOrder;
import com.atguigu.wms.vo.inbound.InOrderQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InOrderMapper extends BaseMapper<InOrder> {
    IPage<InOrder> selectPage(Page<InOrder> page, @Param("vo") InOrderQueryVo inOrderQueryVo);
}
