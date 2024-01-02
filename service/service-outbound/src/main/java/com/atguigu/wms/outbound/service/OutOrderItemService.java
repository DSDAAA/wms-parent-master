package com.atguigu.wms.outbound.service;

import com.atguigu.wms.enums.OutOrderItemStatus;
import com.atguigu.wms.model.outbound.OutOrderItem;
import com.atguigu.wms.vo.outbound.OutOrderItemQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface OutOrderItemService extends IService<OutOrderItem> {

    IPage<OutOrderItem> selectPage(Page<OutOrderItem> pageParam, OutOrderItemQueryVo outOrderItemQueryVo);

    List<OutOrderItem> findByOutOrderId(Long outOrderId);

    void updateBatchStatusByPickingTaskId(Long pickingTaskId, OutOrderItemStatus status);

    void updateBatchStatusByOutOrderId(Long outOrderId, OutOrderItemStatus status);
}
