package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.StorehouseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StorehouseInfoMapper extends BaseMapper<StorehouseInfo> {


}
