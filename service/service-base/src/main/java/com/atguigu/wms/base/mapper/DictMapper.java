package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.Dict;
import com.atguigu.wms.vo.base.DictEeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(@Param("list") List<DictEeVo> list);
}
