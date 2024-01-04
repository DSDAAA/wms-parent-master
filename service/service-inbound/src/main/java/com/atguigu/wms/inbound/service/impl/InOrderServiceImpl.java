package com.atguigu.wms.inbound.service.impl;

import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.DateUtil;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.enums.InPutawayTaskStatus;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.inbound.service.*;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inbound.*;
import com.atguigu.wms.vo.inbound.InOrderQueryVo;
import com.atguigu.wms.inbound.mapper.InOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InOrderServiceImpl extends ServiceImpl<InOrderMapper, InOrder> implements InOrderService {

    @Resource
    private InOrderMapper inOrderMapper;

    @Resource
    private InOrderItemService inOrderItemService;

    @Resource
    private GoodsInfoFeignClient goodsInfoFeignClient;

    @Resource
    private InApproveTaskService inApproveTaskService;

    @Resource
    private InPutawayTaskService inPutawayTaskService;

    @Resource
    private InReceivingTaskService inReceivingTaskService;

    @Resource
    private WarehouseInfoFeignClient warehouseInfoFeignClient;

    @Override
    public IPage<InOrder> selectPage(Page<InOrder> pageParam, InOrderQueryVo inOrderQueryVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (inOrderQueryVo != null) {
            Integer status = inOrderQueryVo.getStatus();
            String no = inOrderQueryVo.getNo();
            Long warehouseId = inOrderQueryVo.getWarehouseId();
            String createTimeEnd = inOrderQueryVo.getCreateTimeEnd();
            String createTimeBegin = inOrderQueryVo.getCreateTimeBegin();
            String estimatedArrivalTimeBegin = inOrderQueryVo.getEstimatedArrivalTimeBegin();
            String estimatedArrivalTimeEnd = inOrderQueryVo.getEstimatedArrivalTimeEnd();
            String shipperName = inOrderQueryVo.getShipperName();
            if (status != 0) {
                queryWrapper.eq("status", status);
            }
            if (!StringUtils.isEmpty(no)) {
                queryWrapper.eq("in_order_no", no);
            }
            if (!StringUtils.isEmpty(createTimeEnd) && !StringUtils.isEmpty(createTimeBegin)) {
                queryWrapper.between("update_name", createTimeBegin, createTimeEnd);
            }
            if (!StringUtils.isEmpty(estimatedArrivalTimeBegin) && !StringUtils.isEmpty(estimatedArrivalTimeEnd)) {
                queryWrapper.between("estimated_arrival_time", estimatedArrivalTimeBegin, estimatedArrivalTimeEnd);
            }
            if (warehouseId != 0) {
                queryWrapper.eq("warehouse_id", warehouseId);
            }
            if (!StringUtils.isEmpty(shipperName)) {
                queryWrapper.eq("shipper_name", shipperName);
            }
        }
        queryWrapper.eq("is_deleted", 0);
        IPage<InOrder> ipage = inOrderMapper.selectPage(pageParam, queryWrapper);
        return ipage;
    }
}
