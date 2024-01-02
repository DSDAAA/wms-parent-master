package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.WareConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WareConfigMapper extends BaseMapper<WareConfig> {

}
