package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.*;
import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.model.base.*;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.base.mapper.GoodsInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private DictService dictService;

    @Resource
    private WarehouseInfoService warehouseInfoService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private GoodsSkuRelationService goodsSkuRelationService;


    private GoodsInfo packageGoodsInfo(GoodsInfo item) {
        item.setTemperatureTypeName(dictService.getNameById(item.getTemperatureTypeId()));
        item.setInspectTypeName(dictService.getNameById(item.getInspectTypeId()));
        item.setGoodsTypeName(goodsTypeService.getNameById(item.getGoodsTypeId()));
        item.setUnitName(dictService.getNameById(item.getUnitId()));
        item.setBaseUnitName(dictService.getNameById(item.getBaseUnitId()));
        return item;
    }

    @Override
    public List<GoodsInfo> findByKeyword(String keyword) {
        LambdaQueryWrapper<GoodsInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(GoodsInfo::getName, keyword);
        List<GoodsInfo> list = this.list(queryWrapper);
        list.forEach(item -> {
            this.packageGoodsInfo(item);
        });
        return list;
    }

    @Override
    public GoodsInfo getGoodsInfo(Long id) {
        GoodsInfo goodsInfo = this.getById(id);
        GoodsSkuRelation goodsSkuRelation = goodsSkuRelationService.getByGoodsId(id);
        if (null != goodsSkuRelation) {
            goodsInfo.setSkuId(goodsSkuRelation.getSkuId());
        }
        return this.packageGoodsInfo(goodsInfo);
    }

    @Override
    public GoodsInfo getGoodsInfoBySkuId(Long skuId) {
        GoodsSkuRelation goodsSkuRelation = goodsSkuRelationService.getBySkuId(skuId);
        if (null == goodsSkuRelation) return null;
        GoodsInfo goodsInfo = this.getById(goodsSkuRelation.getGoodsId());
        if (null != goodsInfo) {
            goodsInfo.setSkuId(skuId);
        }
        return goodsInfo;
    }


    /**
     * 根据第三级分类id获取货品三级分类id列表
     *
     * @param goodsTypeId
     * @return
     */
    @Override
    public List<String> findGoodsTypeIdList(Long goodsTypeId) {
        GoodsType goodsType1 = goodsTypeService.getById(goodsTypeId);
        GoodsType goodsType2 = goodsTypeService.getById(goodsType1.getParentId());
        List<String> list = new ArrayList<>();
        list.add(goodsType2.getParentId().toString());
        list.add(goodsType1.getParentId().toString());
        list.add(goodsTypeId.toString());
        return list;
    }

    /**
     * 分页查询货品列表信息
     * Path：http://192.168.200.1/admin/base/goodsInfo/{page}/{limit}
     * Method：Get
     *
     * @param retPage
     * @param goodsInfoQueryVo
     * @return
     */
    @Override
    public IPage<GoodsInfo> getPageList(Page<GoodsInfo> retPage, GoodsInfoQueryVo goodsInfoQueryVo) {
        Long goodsTypeId = goodsInfoQueryVo.getGoodsTypeId();
        String keyword = goodsInfoQueryVo.getKeyword();
        Integer status = goodsInfoQueryVo.getStatus();
        Long temperatureTypeId = goodsInfoQueryVo.getTemperatureTypeId();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like("name", keyword);
        }
        if (goodsTypeId != 0) {
            queryWrapper.eq("goods_type_id", goodsTypeId);
        }
        if (status != 0) {
            queryWrapper.eq("status", status);
        }
        if (temperatureTypeId != 0) {
            queryWrapper.eq("temperature_type_id", temperatureTypeId);
        }
        IPage<GoodsInfo> ipage = goodsInfoMapper.selectPage(retPage, queryWrapper);
        return ipage;
    }


}
