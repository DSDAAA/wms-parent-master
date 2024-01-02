package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.GoodsSkuRelation;
import com.atguigu.wms.vo.base.GoodsSkuRelationQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsSkuRelationMapper extends BaseMapper<GoodsSkuRelation> {

    IPage<GoodsSkuRelation> selectPage(Page<GoodsSkuRelation> page, @Param("vo") GoodsSkuRelationQueryVo goodsSkuRelationQueryVo);

}
