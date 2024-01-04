package com.atguigu.wms.inbound.service.impl;

import com.atguigu.wms.base.client.GoodsInfoFeignClient;
import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.common.security.AuthContextHolder;
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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        IPage<InOrder> page = inOrderMapper.selectPage(pageParam, inOrderQueryVo);
        page.getRecords().forEach(item -> {
            item.setStatusName(item.getStatus().getComment());
            item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
        });
        return page;
    }

    @Override
    public InOrder getById(Serializable id) {
        InOrder inOrder = inOrderMapper.selectById(id);
        inOrder.setStatusName(inOrder.getStatus().getComment());
        inOrder.setWarehouseName(warehouseInfoFeignClient.getNameById(inOrder.getWarehouseId()));
        List<InOrderItem> inOrderItemList = inOrderItemService.findByInOrderId(inOrder.getId());
        inOrder.setInOrderItemList(inOrderItemList);
        return inOrder;
    }

    @Override
    public Map<String, Object> show(Long id) {
        InOrder inOrder = this.getById(id);
        List<InApproveTask> inApproveTaskList = inApproveTaskService.findByInOrderid(id).stream().filter(item -> item.getStatus() != InTaskStatus.PENDING_APPROVEL).collect(Collectors.toList());
        List<InReceivingTask> inReceivingTaskList = inReceivingTaskService.findByInOrderid(id).stream().filter(item -> item.getStatus() != InTaskStatus.PENDING_APPROVEL).collect(Collectors.toList());
        List<InPutawayTask> inPutawayTaskList = inPutawayTaskService.findByInOrderid(id).stream().filter(item -> item.getStatus() != InPutawayTaskStatus.PENDING_APPROVEL).collect(Collectors.toList());

        inApproveTaskList.forEach(item -> {
            item.setStatusName(item.getStatus().getComment());
        });
        inReceivingTaskList.forEach(item -> {
            item.setStatusName(item.getStatus().getComment());
        });
        inPutawayTaskList.forEach(item -> {
            item.setStatusName(item.getStatus().getComment());
        });
        Map<String, Object> result = new HashMap<>();
        result.put("inOrder", inOrder);
        result.put("inApproveTaskList", inApproveTaskList);
        result.put("inReceivingTaskList", inReceivingTaskList);
        result.put("inPutawayTaskList", inPutawayTaskList);
        return result;
    }

    @Override
    public InOrder getByInOrderNo(String inOrderNo) {
        return this.getOne(new LambdaQueryWrapper<InOrder>().eq(InOrder::getInOrderNo, inOrderNo));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveInOrder(InOrder inOrder) {
        inOrder.setInOrderNo(NoUtils.getOrderNo());
        inOrder.setStatus(InOrderStatus.CREATE);
        inOrder.setCreateId(AuthContextHolder.getUserId());
        inOrder.setCreateName(AuthContextHolder.getUserName());
        inOrderMapper.insert(inOrder);

        int totalExpectCount = 0;
        List<InOrderItem> inOrderItemList = inOrder.getInOrderItemList();
        for(InOrderItem item : inOrderItemList) {
            item.setInOrderId(inOrder.getId());
            item.setWarehouseId(inOrder.getWarehouseId());

            GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfo(item.getGoodsId());
            item.setUnitId(goodsInfo.getUnitId());
            item.setBaseUnitId(goodsInfo.getBaseUnitId());
            item.setVolume(goodsInfo.getVolume());
            item.setWeight(goodsInfo.getWeight());
            item.setBaseCount(item.getExpectCount()*goodsInfo.getBaseCount());
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getBaseCount())));
            item.setCreateId(AuthContextHolder.getUserId());
            item.setCreateName(AuthContextHolder.getUserName());

            totalExpectCount += item.getExpectCount();
        }
        inOrderItemService.saveBatch(inOrderItemList);

        inOrder.setExpectCount(totalExpectCount);
        inOrderMapper.updateById(inOrder);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateInOrder(InOrder inOrder) {
        inOrderMapper.updateById(inOrder);

        inOrderItemService.remove(new LambdaQueryWrapper<InOrderItem>().eq(InOrderItem::getInOrderId, inOrder.getId()));
        List<InOrderItem> inOrderItemList = inOrder.getInOrderItemList();
        inOrderItemList.forEach(item -> {
            item.setInOrderId(inOrder.getId());
            item.setWarehouseId(inOrder.getWarehouseId());

            GoodsInfo goodsInfo = goodsInfoFeignClient.getGoodsInfo(item.getGoodsId());
            item.setUnitId(goodsInfo.getUnitId());
            item.setBaseUnitId(goodsInfo.getBaseUnitId());
            item.setVolume(goodsInfo.getVolume());
            item.setWeight(goodsInfo.getWeight());
            item.setBaseCount(item.getExpectCount()*goodsInfo.getBaseCount());
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getBaseCount())));
        });
        inOrderItemService.saveBatch(inOrderItemList);
    }
}
