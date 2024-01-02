package com.atguigu.wms.inbound.service;

import com.atguigu.wms.model.inbound.InPutawayTask;
import com.atguigu.wms.model.inbound.InReceivingTask;
import com.atguigu.wms.vo.inbound.InPutawayFormVo;
import com.atguigu.wms.vo.inbound.InPutawayTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InPutawayTaskService extends IService<InPutawayTask> {

    IPage<InPutawayTask> selectPage(Page<InPutawayTask> pageParam, InPutawayTaskQueryVo inPutawayTaskQueryVo);

    List<InPutawayTask> findByInOrderid(Long inOrderId);

    void putaway(InPutawayFormVo inPutawayFormVo);

    void syncInventory(Long id);
}
