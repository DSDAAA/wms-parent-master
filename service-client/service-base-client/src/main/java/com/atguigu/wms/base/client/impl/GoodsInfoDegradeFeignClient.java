package com.atguigu.wms.base.client.impl;


import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsInfoDegradeFeignClient implements GoodsInfoFeignClient {


    @Override
    public PageVo<GoodsInfo> findPage(Long page, Long limit, GoodsInfoQueryVo goodsInfoQueryVo) {
        return new PageVo();
    }

    @Override
    public List<GoodsInfo> findList(List<Long> idList) {
        return new ArrayList<>();
    }

    @Override
    public GoodsInfo getGoodsInfo(Long id) {
        return null;
    }

    @Override
    public GoodsInfo getGoodsInfoBySkuId(Long skuId) {
        return null;
    }
}
