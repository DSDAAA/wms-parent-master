package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface StoreareaInfoService extends IService<StoreareaInfo> {


    String getNameById(Long id);

    IPage<StoreareaInfo> getPageList(Page<StoreareaInfo> retPage, StoreareaInfoQueryVo storeareaInfoQueryVo);

    StoreareaInfo get(Integer id);

    void update(StoreareaInfo storeareaInfo);

    void remove(Integer id);
}
