package com.atguigu.wms.outbound.service;

import com.atguigu.wms.model.outbound.OutDeliverTask;
import com.atguigu.wms.vo.outbound.OutDeliverFormVo;
import com.atguigu.wms.vo.outbound.OutDeliverTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface OutDeliverTaskService extends IService<OutDeliverTask> {

    IPage<OutDeliverTask> selectPage(Page<OutDeliverTask> pageParam, OutDeliverTaskQueryVo outDeliverTaskQueryVo);

    void deliver(OutDeliverFormVo outDeliverFormVo);

    List<OutDeliverTask> findByOutOrderId(Long outOrderId);
}
