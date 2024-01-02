package com.atguigu.wms.outbound.service;

import com.atguigu.wms.enums.OutOrderStatus;
import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.vo.outbound.OutOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface OutOrderService extends IService<OutOrder> {

    IPage<OutOrder> selectPage(Page<OutOrder> pageParam, OutOrderQueryVo outOrderQueryVo);

    OutOrder getOutOrderById(Long id);

    Map<String, Object> show(Long id);

    void  saveOutOrderList(List<OutOrder> outOrderList);

    void  saveOutOrder(OutOrder outOrder);

    void updateStatus(Long id, OutOrderStatus status);

    OutOrder getByOutOrderNo(String outOrderNo);

    Boolean updateFinishStatus(Long id);
}
