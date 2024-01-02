package com.atguigu.wms.outbound.mapper;

import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.vo.outbound.OutOrderQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OutOrderMapper extends BaseMapper<OutOrder> {

    IPage<OutOrder> selectPage(Page<OutOrder> page, @Param("vo") OutOrderQueryVo outOrderQueryVo);

}
