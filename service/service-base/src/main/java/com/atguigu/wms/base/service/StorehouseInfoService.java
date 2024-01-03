package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface StorehouseInfoService extends IService<StorehouseInfo> {

    IPage<StorehouseInfo> getPageList(Page<StorehouseInfo> retPage, StorehouseInfoQueryVo storehouseInfoQueryVo);

    void saveStorehouse(StorehouseInfo storehouseInfo);

    StorehouseInfo getStorehouseInfo(Integer id);

    void update(StorehouseInfo storehouseInfo);

    void remove(Integer id);
}
