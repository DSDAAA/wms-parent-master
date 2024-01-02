package com.atguigu.wms.inbound.service;

import com.atguigu.wms.model.inbound.InOrderItem;
import com.atguigu.wms.vo.inbound.InOrderItemQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InOrderItemService extends IService<InOrderItem> {

    IPage<InOrderItem> selectPage(Page<InOrderItem> pageParam, InOrderItemQueryVo inOrderItemQueryVo);

    List<InOrderItem> findByInOrderId(Long inOrderId);
}
