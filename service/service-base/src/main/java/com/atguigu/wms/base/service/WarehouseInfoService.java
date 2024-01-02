package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WarehouseInfoService extends IService<WarehouseInfo> {



    String getNameById(Long id);

    List<Map<String, Object>> findNodes();

    List<String> findNameByIdList(List<Long> idSet);

    List<Long> findPriorityWarehouseIdList(OutOrderAddressVo outOrderAddressVo);





}
