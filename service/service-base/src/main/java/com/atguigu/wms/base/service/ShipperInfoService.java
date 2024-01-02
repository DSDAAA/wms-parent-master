package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ShipperInfoService extends IService<ShipperInfo> {
    ShipperInfo get(Integer id);

    void update(ShipperInfo shipperInfo);

    void remove(Integer id);

    IPage<ShipperInfo> getPageList(IPage<ShipperInfo> retPage, ShipperInfoQueryVo shipperInfoQueryVo);
}
