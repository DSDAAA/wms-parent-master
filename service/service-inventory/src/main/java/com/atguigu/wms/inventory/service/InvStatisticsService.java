package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface InvStatisticsService extends IService<InvStatistics> {

    Map<String, Object> getByTpye(String type);
}
