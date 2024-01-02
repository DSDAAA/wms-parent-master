package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.model.base.GoodsSkuRelation;
import com.atguigu.wms.vo.base.GoodsSkuRelationQueryVo;
import com.atguigu.wms.base.mapper.GoodsSkuRelationMapper;
import com.atguigu.wms.base.service.GoodsSkuRelationService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsSkuRelationServiceImpl extends ServiceImpl<GoodsSkuRelationMapper, GoodsSkuRelation> implements GoodsSkuRelationService {

	@Resource
	private GoodsSkuRelationMapper goodsSkuRelationMapper;


    @Override
    public GoodsSkuRelation getByGoodsId(Long goodsId) {

        return this.getOne(new LambdaQueryWrapper<GoodsSkuRelation>().eq(GoodsSkuRelation::getGoodsId, goodsId));
    }

	@Override
	public GoodsSkuRelation getBySkuId(Long skuId) {
		return this.getOne(new LambdaQueryWrapper<GoodsSkuRelation>().eq(GoodsSkuRelation::getSkuId, skuId));
	}


}
