package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.GoodsSkuRelation;
import com.atguigu.wms.vo.base.GoodsSkuRelationQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface GoodsSkuRelationService extends IService<GoodsSkuRelation> {

    GoodsSkuRelation getByGoodsId(Long goodsId);

    GoodsSkuRelation getBySkuId(Long skuId);

}
