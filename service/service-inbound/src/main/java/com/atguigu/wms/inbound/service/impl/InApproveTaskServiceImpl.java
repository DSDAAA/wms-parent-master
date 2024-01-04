package com.atguigu.wms.inbound.service.impl;


import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.inbound.service.InOrderService;
import com.atguigu.wms.inbound.service.InReceivingTaskService;
import com.atguigu.wms.model.inbound.InApproveTask;
import com.atguigu.wms.model.inbound.InOrder;
import com.atguigu.wms.model.inbound.InReceivingTask;
import com.atguigu.wms.vo.inbound.InApproveFormVo;
import com.atguigu.wms.vo.inbound.InApproveTaskQueryVo;
import com.atguigu.wms.inbound.mapper.InApproveTaskMapper;
import com.atguigu.wms.inbound.service.InApproveTaskService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InApproveTaskServiceImpl extends ServiceImpl<InApproveTaskMapper, InApproveTask> implements InApproveTaskService {

    @Resource
    private InApproveTaskMapper inApproveTaskMapper;

    @Resource
    private InOrderService inOrderService;

    @Resource
    private InReceivingTaskService inReceivingTaskService;

    @Override
    public IPage<InApproveTask> selectPage(Page<InApproveTask> pageParam, InApproveTaskQueryVo inApproveTaskQueryVo) {
        IPage<InApproveTask> page = inApproveTaskMapper.selectPage(pageParam, inApproveTaskQueryVo);
        page.getRecords().forEach(item -> {
            item.setStatusName(item.getStatus().getComment());
        });
        return page;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void submitApprove(Long inOrderId) {
        InOrder inOrder = inOrderService.getById(inOrderId);
        inOrder.setStatus(InOrderStatus.APPROVEL_RUN);
        inOrderService.updateById(inOrder);

        InApproveTask inApproveTask = new InApproveTask();
        inApproveTask.setTaskNo(NoUtils.getOrderNo());
        inApproveTask.setInOrderNo(inOrder.getInOrderNo());
        inApproveTask.setInOrderId(inOrder.getId());
        inApproveTask.setShipperOrderNo(inOrder.getShipperOrderNo());
        inApproveTask.setShipperId(inOrder.getShipperId());
        inApproveTask.setShipperName(inOrder.getShipperName());
        inApproveTask.setWarehouseId(inOrder.getWarehouseId());
        inApproveTask.setDriver(inOrder.getDriver());
        inApproveTask.setDriverPhone(inOrder.getDriverPhone());
        inApproveTask.setExpectCount(inOrder.getExpectCount());
        inApproveTask.setStatus(InTaskStatus.PENDING_APPROVEL);
        this.save(inApproveTask);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void approve(InApproveFormVo inApproveFromVo) {
        InApproveTask inApproveTask = this.getById(inApproveFromVo.getId());
        if (inApproveFromVo.getStatus() == 1) {
            inApproveTask.setStatus(InTaskStatus.FINISH);
            inApproveTask.setRemarks(inApproveFromVo.getRemarks());
            inApproveTask.setApproveTime(new Date());
            inApproveTask.setApproveUserId(AuthContextHolder.getUserId());
            inApproveTask.setApproveUser(AuthContextHolder.getUserName());
            this.updateById(inApproveTask);

            InOrder inOrder = inOrderService.getById(inApproveTask.getInOrderId());
            inOrder.setStatus(InOrderStatus.RECEIVING_RUN);
            inOrderService.updateById(inOrder);

            InReceivingTask inReceivingTask = new InReceivingTask();
            inReceivingTask.setTaskNo(NoUtils.getOrderNo());
            inReceivingTask.setInOrderId(inOrder.getId());
            inReceivingTask.setInOrderNo(inOrder.getInOrderNo());
            inReceivingTask.setShipperOrderNo(inOrder.getShipperOrderNo());
            inReceivingTask.setShipperId(inOrder.getShipperId());
            inReceivingTask.setShipperName(inOrder.getShipperName());
            inReceivingTask.setWarehouseId(inOrder.getWarehouseId());
            inReceivingTask.setEstimatedArrivalTime(inOrder.getEstimatedArrivalTime());
            inReceivingTask.setDriver(inOrder.getDriver());
            inReceivingTask.setDriverPhone(inOrder.getDriverPhone());
            inReceivingTask.setExpectCount(inOrder.getExpectCount());
            inReceivingTask.setStatus(InTaskStatus.PENDING_APPROVEL);
            inReceivingTaskService.save(inReceivingTask);
        } else {
            inApproveTask.setStatus(InTaskStatus.REJECT);
            inApproveTask.setRemarks(inApproveFromVo.getRemarks());
            inApproveTask.setApproveTime(new Date());
            inApproveTask.setApproveUserId(AuthContextHolder.getUserId());
            inApproveTask.setApproveUser(AuthContextHolder.getUserName());
            this.updateById(inApproveTask);

            InOrder inOrder = inOrderService.getByInOrderNo(inApproveTask.getInOrderNo());
            inOrder.setStatus(InOrderStatus.REJECT);
            inOrderService.updateById(inOrder);
        }
    }

    @Override
    public List<InApproveTask> findByInOrderid(Long inOrderId) {
        return this.list(new LambdaQueryWrapper<InApproveTask>().eq(InApproveTask::getInOrderId, inOrderId));
    }

}
