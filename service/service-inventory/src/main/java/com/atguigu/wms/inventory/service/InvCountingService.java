package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvCounting;
import com.atguigu.wms.vo.inventory.InvCountingQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InvCountingService extends IService<InvCounting> {

    IPage<InvCounting> selectPage(Page<InvCounting> pageParam, InvCountingQueryVo invCountingQueryVo);

    void saveInvCounting(InvCounting invCounting);

    void updateInvCounting(InvCounting invCounting);

    InvCounting getInvCountingById(Long id);

    Map<String, Object> show(Long id);
}
