package com.atguigu.wms.inbound.service.impl;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.enums.InPutawayTaskStatus;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.inbound.service.InOrderItemService;
import com.atguigu.wms.inbound.service.InOrderService;
import com.atguigu.wms.inbound.service.InPutawayTaskService;
import com.atguigu.wms.model.inbound.*;
import com.atguigu.wms.vo.inbound.InReceivingFormVo;
import com.atguigu.wms.vo.inbound.InReceivingItemVo;
import com.atguigu.wms.vo.inbound.InReceivingTaskQueryVo;
import com.atguigu.wms.inbound.mapper.InReceivingTaskMapper;
import com.atguigu.wms.inbound.service.InReceivingTaskService;
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
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InReceivingTaskServiceImpl extends ServiceImpl<InReceivingTaskMapper, InReceivingTask> implements InReceivingTaskService {

	@Resource
	private InReceivingTaskMapper inReceivingTaskMapper;

	@Resource
	private InOrderService inOrderService;

	@Resource
	private InOrderItemService inOrderItemService;

	@Resource
	private InPutawayTaskService inPutawayTaskService;

	@Override
	public IPage<InReceivingTask> selectPage(Page<InReceivingTask> pageParam, InReceivingTaskQueryVo inReceivingTaskQueryVo) {
		IPage<InReceivingTask> page = inReceivingTaskMapper.selectPage(pageParam, inReceivingTaskQueryVo);
		page.getRecords().forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});
		return page;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void receiving(InReceivingFormVo inReceivingFormVo) {
		if(inReceivingFormVo.getStatus() == 1) {
			InReceivingTask inReceivingTask = this.getById(inReceivingFormVo.getId());
			inReceivingTask.setStatus(InTaskStatus.FINISH);
			inReceivingTask.setRemarks(inReceivingFormVo.getRemarks());
			inReceivingTask.setReceivingTime(new Date());
			inReceivingTask.setReceivingUserId(AuthContextHolder.getUserId());
			inReceivingTask.setReceivingUser(AuthContextHolder.getUserName());
			this.updateById(inReceivingTask);

			InOrder inOrder = inOrderService.getById(inReceivingTask.getInOrderId());
			inOrder.setStatus(InOrderStatus.PUTAWAY_RUN);
			inOrderService.updateById(inOrder);

			int totalActualCount = 0;
			Map<Long, InReceivingItemVo> goodsIdToInReceivingItemVoMap = inReceivingFormVo.getInReceivingItemVoList().stream().collect(Collectors.toMap(InReceivingItemVo::getGoodsId, InReceivingItemVo -> InReceivingItemVo));
			List<InOrderItem> inOrderItemList = inOrder.getInOrderItemList();
			for(InOrderItem inOrderItem : inOrderItemList) {
				InReceivingItemVo inReceivingItemVo = goodsIdToInReceivingItemVoMap.get(inOrderItem.getGoodsId());
				inOrderItem.setActualCount(inReceivingItemVo.getActualCount());
				inOrderItemService.updateById(inOrderItem);

				totalActualCount += inReceivingItemVo.getActualCount();
			}
			//更新实际到货数量
			inReceivingTask.setActualCount(totalActualCount);
			this.updateById(inReceivingTask);

			InPutawayTask inPutawayTask = new InPutawayTask();
			inPutawayTask.setTaskNo(NoUtils.getOrderNo());
			inPutawayTask.setInOrderId(inOrder.getId());
			inPutawayTask.setInOrderNo(inOrder.getInOrderNo());
			inPutawayTask.setShipperOrderNo(inOrder.getShipperOrderNo());
			inPutawayTask.setShipperId(inOrder.getShipperId());
			inPutawayTask.setShipperName(inOrder.getShipperName());
			inPutawayTask.setWarehouseId(inOrder.getWarehouseId());
			inPutawayTask.setExpectCount(inOrder.getExpectCount());
			inPutawayTask.setActualCount(inReceivingTask.getActualCount());
			inPutawayTask.setStatus(InPutawayTaskStatus.PENDING_APPROVEL);
			inPutawayTaskService.save(inPutawayTask);
		} else {
			InReceivingTask inReceivingTask = this.getById(inReceivingFormVo.getId());
			inReceivingTask.setStatus(InTaskStatus.REJECT);
			inReceivingTask.setRemarks(inReceivingFormVo.getRemarks());
			inReceivingTask.setReceivingTime(new Date());
			inReceivingTask.setReceivingUserId(AuthContextHolder.getUserId());
			inReceivingTask.setReceivingUser(AuthContextHolder.getUserName());
			this.updateById(inReceivingTask);

			InOrder inOrder = inOrderService.getById(inReceivingTask.getInOrderId());
			inOrder.setStatus(InOrderStatus.REJECT);
			inOrderService.updateById(inOrder);
		}
	}

	@Override
	public List<InReceivingTask> findByInOrderid(Long inOrderId) {
		return this.list(new LambdaQueryWrapper<InReceivingTask>().eq(InReceivingTask::getInOrderId, inOrderId));
	}

}
