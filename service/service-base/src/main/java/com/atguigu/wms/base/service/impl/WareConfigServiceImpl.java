package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.model.base.WareConfig;
import com.atguigu.wms.base.mapper.WareConfigMapper;
import com.atguigu.wms.base.service.WareConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class WareConfigServiceImpl extends ServiceImpl<WareConfigMapper, WareConfig> implements WareConfigService {

	@Resource
	private WareConfigMapper waveConfigMapper;


}
