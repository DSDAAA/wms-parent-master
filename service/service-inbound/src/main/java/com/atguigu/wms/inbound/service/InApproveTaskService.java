package com.atguigu.wms.inbound.service;

import com.atguigu.wms.model.inbound.InApproveTask;
import com.atguigu.wms.vo.inbound.InApproveFormVo;
import com.atguigu.wms.vo.inbound.InApproveTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface InApproveTaskService extends IService<InApproveTask> {

    IPage<InApproveTask> selectPage(Page<InApproveTask> pageParam, InApproveTaskQueryVo inApproveTaskQueryVo);

    void submitApprove(Long inOrderId);

    void approve(InApproveFormVo inApproveFromVo);

    List<InApproveTask> findByInOrderid(Long inOrderId);
}
