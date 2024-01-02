package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.GoodsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

}
