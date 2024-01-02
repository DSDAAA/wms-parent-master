package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvMoveItem;
import com.atguigu.wms.model.inventory.InvMoveTask;
import com.atguigu.wms.vo.inventory.InvMoveTaskFormVo;
import com.atguigu.wms.vo.inventory.InvMoveTaskQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InvMoveTaskService extends IService<InvMoveTask> {

    IPage<InvMoveTask> selectPage(Page<InvMoveTask> pageParam, InvMoveTaskQueryVo invMoveTaskQueryVo);

    List<InvMoveTask> findByInvMoveId(Long invMoveId);

    void assign(InvMoveTaskFormVo invMoveTaskFormVo);

    void updateInvMoveItem(List<InvMoveItem> invMoveItemList);

    void syncInventory(Long id);
}
