package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface StoreshelfInfoService extends IService<StoreshelfInfo> {

    IPage<StoreshelfInfo> getPageList(Page<StoreshelfInfo> retPage, StoreshelfInfoQueryVo storeshelfInfoQueryVo);

    void saveStoreshelf(StoreshelfInfo storeshelfInfo);

    StoreshelfInfo get(Integer id);

    void update(StoreshelfInfo storeshelfInfo);

    void remove(Integer id);

    List<StoreareaInfo> findByStoreareaId(Integer storeareaId);
}
