package com.atguigu.wms.outbound.service;

import com.atguigu.wms.model.outbound.OutDeliverTask;
import com.atguigu.wms.model.outbound.OutPickingTask;
import com.atguigu.wms.vo.outbound.OutPickingTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface OutPickingTaskService extends IService<OutPickingTask> {

    IPage<OutPickingTask> selectPage(Page<OutPickingTask> pageParam, OutPickingTaskQueryVo outPickingTaskQueryVo);

    OutPickingTask getOutPickingTask(Long id);

    void run();

    void finish(Long id);

    List<OutPickingTask> findByOutOrderId(Long outOrderId);
}
