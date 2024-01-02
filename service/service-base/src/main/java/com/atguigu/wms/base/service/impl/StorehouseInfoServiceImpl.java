package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.base.service.StoreshelfInfoService;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.atguigu.wms.base.mapper.StorehouseInfoMapper;
import com.atguigu.wms.base.service.StorehouseInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class StorehouseInfoServiceImpl extends ServiceImpl<StorehouseInfoMapper, StorehouseInfo> implements StorehouseInfoService {




	@Override
	public List<StorehouseInfo> findByStoreshelfId(Long storeshelfId) {
		return this.list(new LambdaQueryWrapper<StorehouseInfo>().eq(StorehouseInfo::getStoreshelfId, storeshelfId));
	}

	@Cacheable(value = "storehouseInfo",keyGenerator = "keyGenerator")
	@Override
	public String getNameById(Long id) {
		if(null == id) return "";
		StorehouseInfo storehouseInfo = this.getById(id);
		return storehouseInfo.getName();
	}

	@Override
	public List<String> findNameByIdList(List<Long> idList) {
		List<String> list = new ArrayList<>();
		for(Long id : idList) {
			list.add(this.getNameById(id));
		}
		return list;
	}






}
