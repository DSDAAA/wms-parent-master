package com.atguigu.wms.inventory.service;

import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.model.inventory.InvLog;
import com.atguigu.wms.vo.inventory.InvLogQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InvLogService extends IService<InvLog> {

    IPage<InvLog> selectPage(Page<InvLog> pageParam, InvLogQueryVo invLogQueryVo);

    void log(Long warehouseId, Long goodsId, InvLogType invLogType, Integer alterationCount, String remarks);
}
