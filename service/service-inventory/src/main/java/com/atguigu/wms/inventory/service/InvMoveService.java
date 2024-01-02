package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvCounting;
import com.atguigu.wms.model.inventory.InvMove;
import com.atguigu.wms.vo.inventory.InvMoveQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InvMoveService extends IService<InvMove> {

    IPage<InvMove> selectPage(Page<InvMove> pageParam, InvMoveQueryVo invMoveQueryVo);

    void saveInvMove(InvMove invMove);

    void updateInvMove(InvMove invMove);

    InvMove getInvMoveById(Long id);

    Map<String, Object> show(Long id);
}
