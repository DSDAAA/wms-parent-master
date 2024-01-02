package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvMoveItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface InvMoveItemService extends IService<InvMoveItem> {

    List<InvMoveItem> findByInvMoveId(Long invMoveId);
}
