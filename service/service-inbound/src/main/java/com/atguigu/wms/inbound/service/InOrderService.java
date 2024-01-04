package com.atguigu.wms.inbound.service;

import com.atguigu.wms.model.inbound.InOrder;
import com.atguigu.wms.vo.inbound.InOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InOrderService extends IService<InOrder> {

    IPage<InOrder> selectPage(Page<InOrder> pageParam, InOrderQueryVo inOrderQueryVo);

    Map<String, Object> show(Long id);

    InOrder getByInOrderNo(String inOrderNo);

    void saveInOrder(InOrder inOrder);

    void updateInOrder(InOrder inOrder);
}
