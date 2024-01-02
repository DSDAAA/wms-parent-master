package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.atguigu.wms.base.mapper.StoreshelfInfoMapper;
import com.atguigu.wms.base.service.StoreshelfInfoService;
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
public class StoreshelfInfoServiceImpl extends ServiceImpl<StoreshelfInfoMapper, StoreshelfInfo> implements StoreshelfInfoService {




	@Cacheable(value = "storeshelfInfo",keyGenerator = "keyGenerator")
	@Override
	public String getNameById(Long id) {
		if(null == id) return "";
		StoreshelfInfo storeshelfInfo = this.getById(id);
		return storeshelfInfo.getName();
	}




}
