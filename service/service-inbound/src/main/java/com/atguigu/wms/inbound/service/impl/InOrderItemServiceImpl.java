package com.atguigu.wms.inbound.service.impl;

import com.atguigu.wms.base.client.DictFeignClient;
import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inbound.InOrderItem;
import com.atguigu.wms.vo.inbound.InOrderItemQueryVo;
import com.atguigu.wms.inbound.mapper.InOrderItemMapper;
import com.atguigu.wms.inbound.service.InOrderItemService;
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
public class InOrderItemServiceImpl extends ServiceImpl<InOrderItemMapper, InOrderItem> implements InOrderItemService {

	@Resource
	private InOrderItemMapper inOrderItemMapper;

	@Autowired
	private GoodsInfoFeignClient goodsInfoFeignClient;

	@Resource
	private DictFeignClient dictFeignClient;

	@Override
	public IPage<InOrderItem> selectPage(Page<InOrderItem> pageParam, InOrderItemQueryVo inOrderItemQueryVo) {

		return inOrderItemMapper.selectPage(pageParam, inOrderItemQueryVo);
	}

    @Override
    public List<InOrderItem> findByInOrderId(Long inOrderId) {
		List<InOrderItem> list = this.list(new LambdaQueryWrapper<InOrderItem>().eq(InOrderItem::getInOrderId, inOrderId));
		list.forEach(item -> {
			this.packageInOrderItem(item);
		});
		return list;
    }

	private InOrderItem packageInOrderItem(InOrderItem item) {
		item.setGoodsInfo(goodsInfoFeignClient.getGoodsInfo(item.getGoodsId()));
		item.setUnitName(dictFeignClient.getNameById(item.getUnitId()));
		item.setBaseUnitName(dictFeignClient.getNameById(item.getBaseUnitId()));
		return item;
	}

}
