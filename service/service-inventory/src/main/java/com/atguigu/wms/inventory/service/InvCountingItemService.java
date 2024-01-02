package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvCountingItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface InvCountingItemService extends IService<InvCountingItem> {

    List<InvCountingItem> findByInvCountingId(Long invCountingId);
}
