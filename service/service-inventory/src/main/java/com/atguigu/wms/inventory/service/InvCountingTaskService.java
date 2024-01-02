package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvCountingItem;
import com.atguigu.wms.model.inventory.InvCountingTask;
import com.atguigu.wms.vo.inventory.InvCountingTaskFormVo;
import com.atguigu.wms.vo.inventory.InvCountingTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface InvCountingTaskService extends IService<InvCountingTask> {

    IPage<InvCountingTask> selectPage(Page<InvCountingTask> pageParam, InvCountingTaskQueryVo invCountingTaskQueryVo);

    void assign(InvCountingTaskFormVo invCountingTaskFormVo);

    void updateInvCountingItem(List<InvCountingItem> invCountingItemList);

    void syncInventory(Long id);

    List<InvCountingTask> findByInvCountingId(Long invCountingId);
}
