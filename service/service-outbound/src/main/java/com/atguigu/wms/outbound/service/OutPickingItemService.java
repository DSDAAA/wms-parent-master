package com.atguigu.wms.outbound.service;

import com.atguigu.wms.model.outbound.OutPickingItem;
import com.atguigu.wms.vo.outbound.OutPickingItemQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface OutPickingItemService extends IService<OutPickingItem> {


    List<OutPickingItem> findByOutPickingId(Long outPickingId);
}
