package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StorehouseInfoMapper extends BaseMapper<StorehouseInfo> {


}
