package com.atguigu.wms.outbound.service.impl;

import com.atguigu.wms.base.client.WarehouseInfoFeignClient;
import com.atguigu.wms.common.constant.MqConst;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.service.RabbitService;
import com.atguigu.wms.enums.OutDeliverTaskStatus;
import com.atguigu.wms.enums.OutOrderStatus;
import com.atguigu.wms.model.outbound.OutDeliverTask;
import com.atguigu.wms.model.outbound.OutOrder;
import com.atguigu.wms.model.outbound.OutPickingTask;
import com.atguigu.wms.outbound.service.OutOrderService;
import com.atguigu.wms.vo.outbound.OutDeliverFormVo;
import com.atguigu.wms.vo.outbound.OutDeliverTaskQueryVo;
import com.atguigu.wms.outbound.mapper.OutDeliverTaskMapper;
import com.atguigu.wms.outbound.service.OutDeliverTaskService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OutDeliverTaskServiceImpl extends ServiceImpl<OutDeliverTaskMapper, OutDeliverTask> implements OutDeliverTaskService {

	@Resource
	private OutDeliverTaskMapper outDeliverTaskMapper;

	@Resource
	private OutOrderService outOrderService;

	@Resource
	private WarehouseInfoFeignClient warehouseInfoFeignClient;

	@Resource
	private RabbitService rabbitService;

	@Override
	public IPage<OutDeliverTask> selectPage(Page<OutDeliverTask> pageParam, OutDeliverTaskQueryVo outDeliverTaskQueryVo) {
		IPage<OutDeliverTask> page = outDeliverTaskMapper.selectPage(pageParam, outDeliverTaskQueryVo);
		page.getRecords().forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
			item.setWarehouseName(warehouseInfoFeignClient.getNameById(item.getWarehouseId()));
		});
		return page;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void deliver(OutDeliverFormVo outDeliverFormVo) {
		OutDeliverTask outDeliverTask = this.getById(outDeliverFormVo.getId());
		outDeliverTask.setStatus(OutDeliverTaskStatus.FINISH);
		outDeliverTask.setDeliverUserId(AuthContextHolder.getUserId());
		outDeliverTask.setDeliverUser(AuthContextHolder.getUserName());
		outDeliverTask.setDeliverTime(new Date());
		outDeliverTask.setTrackingNo(outDeliverFormVo.getTrackingNo());
		outDeliverTask.setTrackingCompany(outDeliverFormVo.getTrackingCompany());
		this.updateById(outDeliverTask);

		OutOrder outOrder = outOrderService.getById(outDeliverTask.getOutOrderId());
		outOrder.setStatus(OutOrderStatus.PUTAWAY_RUN);
		outOrder.setTrackingNo(outDeliverFormVo.getTrackingNo());
		outOrder.setTrackingCompany(outDeliverFormVo.getTrackingCompany());
		outOrderService.updateById(outOrder);

		//扣减库存
		rabbitService.sendMessage( MqConst.EXCHANGE_INVENTORY,  MqConst.ROUTING_MINUS, outOrder.getOutOrderNo());
	}

	@Override
	public List<OutDeliverTask> findByOutOrderId(Long outOrderId) {
		List<OutDeliverTask> outDeliverTaskList = this.list(new LambdaQueryWrapper<OutDeliverTask>().eq(OutDeliverTask::getOutOrderId, outOrderId));
		outDeliverTaskList.forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});
		return outDeliverTaskList;
	}

}
