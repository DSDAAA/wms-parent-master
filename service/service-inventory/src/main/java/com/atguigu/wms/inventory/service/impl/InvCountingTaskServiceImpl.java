package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.acl.client.AdminFeignClient;
import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InvCountingStatus;
import com.atguigu.wms.enums.InvCountingTaskStatus;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.enums.InvMoveTaskStatus;
import com.atguigu.wms.inventory.service.InvCountingItemService;
import com.atguigu.wms.inventory.service.InvCountingService;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.model.inbound.InOrderItem;
import com.atguigu.wms.model.inventory.InvCounting;
import com.atguigu.wms.model.inventory.InvCountingItem;
import com.atguigu.wms.model.inventory.InvCountingTask;
import com.atguigu.wms.inventory.mapper.InvCountingTaskMapper;
import com.atguigu.wms.inventory.service.InvCountingTaskService;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.inventory.InvCountingTaskFormVo;
import com.atguigu.wms.vo.inventory.InvCountingTaskQueryVo;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvCountingTaskServiceImpl extends ServiceImpl<InvCountingTaskMapper, InvCountingTask> implements InvCountingTaskService {

	@Resource
	private InvCountingTaskMapper invCountingTaskMapper;

	@Resource
	private InvCountingService invCountingService;

	@Resource
	private InvCountingItemService invCountingItemService;

	@Resource
	private InventoryInfoService inventoryInfoService;

	@Resource
	private AdminFeignClient adminFeignClient;

	@Override
	public IPage<InvCountingTask> selectPage(Page<InvCountingTask> pageParam, InvCountingTaskQueryVo invCountingTaskQueryVo) {
		IPage<InvCountingTask> page = invCountingTaskMapper.selectPage(pageParam, invCountingTaskQueryVo);
		page.getRecords().forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});
		return page;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void assign(InvCountingTaskFormVo invCountingTaskFormVo) {
		InvCounting invCounting = invCountingService.getById(invCountingTaskFormVo.getInvCountingId());
		invCounting.setStatus(InvCountingStatus.PENDING_COUNTING);
		invCountingService.updateById(invCounting);

		InvCountingTask invCountingTask = new InvCountingTask();
		invCountingTask.setInvCountingId(invCountingTaskFormVo.getInvCountingId());
		invCountingTask.setTaskNo(NoUtils.getOrderNo());
		invCountingTask.setInvCountingNo(invCounting.getInvCountingNo());
		invCountingTask.setCountingUserId(invCountingTaskFormVo.getCountingUserId());
		invCountingTask.setCountingUser(invCountingTaskFormVo.getCountingUser());
		invCountingTask.setWarehouseId(invCounting.getWarehouseId());
		invCountingTask.setStatus(InvCountingTaskStatus.PENDING_DEAL);
		invCountingTask.setCreateId(AuthContextHolder.getUserId());
		invCountingTask.setCreateName(AuthContextHolder.getUserName());
		this.save(invCountingTask);
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void updateInvCountingItem(List<InvCountingItem> invCountingItemListVo) {
		Long invCountingId = null;
		int differenceCount = 0;
		for(InvCountingItem invCountingItem : invCountingItemListVo) {
			invCountingId = invCountingItem.getInvCountingId();
			invCountingItemService.updateById(invCountingItem);

			if(invCountingItem.getDifferenceCount() != 0) {
				differenceCount++;
			}
		}

		InvCountingTask invCountingTask = this.getOne(new LambdaQueryWrapper<InvCountingTask>().eq(InvCountingTask::getInvCountingId, invCountingId));
		invCountingTask.setCountingCompleteTime(new Date());
		invCountingTask.setStatus(InvCountingTaskStatus.FINISH);
		this.updateById(invCountingTask);

		InvCounting invCounting = invCountingService.getById(invCountingTask.getInvCountingId());
		invCounting.setStatus(InvCountingStatus.FINISH);
		invCountingService.updateById(invCounting);

		//更新库存
		if(differenceCount > 0) {
			List<InvCountingItem> invCountingItemList = invCountingItemService.list(new LambdaQueryWrapper<InvCountingItem>().eq(InvCountingItem::getInvCountingId, invCountingTask.getInvCountingId()));
			List<InventoryInfoVo> inventoryInfoVoList = new ArrayList<>();
			for(InvCountingItem invCountingItem : invCountingItemList) {
				if(invCountingItem.getDifferenceCount() == 0) continue;

				//盘点属于更新库存
				InventoryInfoVo inventoryInfoVo = new InventoryInfoVo();
				inventoryInfoVo.setGoodsId(invCountingItem.getGoodsId());
				inventoryInfoVo.setWarehouseId(invCounting.getWarehouseId());
				inventoryInfoVo.setPutawayCount(invCountingItem.getDifferenceCount());
				inventoryInfoVoList.add(inventoryInfoVo);
			}
			if(!CollectionUtils.isEmpty(inventoryInfoVoList)) {
				Boolean isPutaway = inventoryInfoService.countingSyncInventory(invCountingTask.getTaskNo(), inventoryInfoVoList, InvLogType.INV_COUNTING);
				if(!isPutaway) {
					throw new WmsException(ResultCodeEnum.DATA_ERROR);
				}
			}
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void syncInventory(Long id) {
//		InvCountingTask invCountingTask = this.getById(id);
//		invCountingTask.setStatus(InvCountingTaskStatus.FINISH);
//		this.updateById(invCountingTask);
//
//		InvCounting invCounting = invCountingService.getById(invCountingTask.getInvCountingId());
//		invCounting.setStatus(InvCountingStatus.FINISH);
//		invCountingService.updateById(invCounting);
//
//		List<InvCountingItem> invCountingItemList = invCountingItemService.list(new LambdaQueryWrapper<InvCountingItem>().eq(InvCountingItem::getInvCountingId, invCountingTask.getInvCountingId()));
//		List<InventoryInfoVo> inventoryInfoVoList = new ArrayList<>();
//		for(InvCountingItem invCountingItem : invCountingItemList) {
//			if(invCountingItem.getDifferenceCount() == 0) continue;
//
//			//盘点属于更新库存
//			InventoryInfoVo inventoryInfoVo = new InventoryInfoVo();
//			inventoryInfoVo.setGoodsId(invCountingItem.getGoodsId());
//			inventoryInfoVo.setWarehouseId(invCounting.getWarehouseId());
//			inventoryInfoVo.setPutawayCount(invCountingItem.getDifferenceCount());
//			inventoryInfoVoList.add(inventoryInfoVo);
//		}
//		if(!CollectionUtils.isEmpty(inventoryInfoVoList)) {
//			Boolean isPutaway = inventoryInfoService.countingSyncInventory(invCountingTask.getTaskNo(), inventoryInfoVoList, InvLogType.INV_COUNTING);
//			if(!isPutaway) {
//				throw new WmsException(ResultCodeEnum.DATA_ERROR);
//			}
//		}
	}

    @Override
    public List<InvCountingTask> findByInvCountingId(Long invCountingId) {
		return this.list(new LambdaQueryWrapper<InvCountingTask>().eq(InvCountingTask::getInvCountingId, invCountingId));
    }

}
