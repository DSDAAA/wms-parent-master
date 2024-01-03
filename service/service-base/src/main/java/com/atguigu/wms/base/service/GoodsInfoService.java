package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService extends IService<GoodsInfo> {

    List<GoodsInfo> findByKeyword(String keyword);

    GoodsInfo getGoodsInfo(Long id);

    GoodsInfo getGoodsInfoBySkuId(Long skuId);


    List<String> findGoodsTypeIdList(Long goodsTypeId);

    IPage<GoodsInfo> getPageList(Page<GoodsInfo> retPage, GoodsInfoQueryVo goodsInfoQueryVo);

    GoodsInfo get(Integer id);

    void update(GoodsInfo goodsInfo);

    void remove(Integer id);

    void updateStatus(Integer id, Integer status);
}
