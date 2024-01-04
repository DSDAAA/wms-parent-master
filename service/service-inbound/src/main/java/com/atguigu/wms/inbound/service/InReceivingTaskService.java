package com.atguigu.wms.inbound.service;

import com.atguigu.wms.model.inbound.InReceivingTask;
import com.atguigu.wms.vo.inbound.InReceivingFormVo;
import com.atguigu.wms.vo.inbound.InReceivingTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface InReceivingTaskService extends IService<InReceivingTask> {

    IPage<InReceivingTask> selectPage(Page<InReceivingTask> pageParam, InReceivingTaskQueryVo inReceivingTaskQueryVo);

    void receiving(InReceivingFormVo inReceivingFormVo);

    List<InReceivingTask> findByInOrderid(Long inOrderId);
}
