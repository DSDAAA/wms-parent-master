package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.enums.InvMoveStatus;
import com.atguigu.wms.enums.InvMoveTaskStatus;
import com.atguigu.wms.inventory.service.InvMoveItemService;
import com.atguigu.wms.inventory.service.InvMoveService;
import com.atguigu.wms.inventory.service.InventoryInfoService;
import com.atguigu.wms.model.inventory.InvMoveTask;
import com.atguigu.wms.model.inventory.InvMove;
import com.atguigu.wms.model.inventory.InvMoveItem;
import com.atguigu.wms.model.inventory.InvMoveTask;
import com.atguigu.wms.vo.inventory.InvMoveTaskFormVo;
import com.atguigu.wms.vo.inventory.InvMoveTaskQueryVo;
import com.atguigu.wms.inventory.mapper.InvMoveTaskMapper;
import com.atguigu.wms.inventory.service.InvMoveTaskService;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvMoveTaskServiceImpl extends ServiceImpl<InvMoveTaskMapper, InvMoveTask> implements InvMoveTaskService {

	@Resource
	private InvMoveTaskMapper invMoveTaskMapper;

	@Resource
	private InvMoveService invMoveService;

	@Resource
	private InvMoveItemService invMoveItemService;

	@Resource
	private InventoryInfoService inventoryInfoService;

	@Override
	public IPage<InvMoveTask> selectPage(Page<InvMoveTask> pageParam, InvMoveTaskQueryVo invMoveTaskQueryVo) {
		IPage<InvMoveTask> page = invMoveTaskMapper.selectPage(pageParam, invMoveTaskQueryVo);
		page.getRecords().forEach(item -> {
			item.setStatusName(item.getStatus().getComment());
		});
		return page;
	}

	@Override
	public List<InvMoveTask> findByInvMoveId(Long invMoveId) {
		return this.list(new LambdaQueryWrapper<InvMoveTask>().eq(InvMoveTask::getInvMoveId, invMoveId));
	}

	@Override
	public void assign(InvMoveTaskFormVo invMoveTaskFormVo) {
		InvMove invMove = invMoveService.getById(invMoveTaskFormVo.getInvMoveId());
		invMove.setStatus(InvMoveStatus.PENDING_COUNTING);
		invMoveService.updateById(invMove);

		InvMoveTask invMoveTask = new InvMoveTask();
		invMoveTask.setInvMoveId(invMoveTaskFormVo.getInvMoveId());
		invMoveTask.setTaskNo(NoUtils.getOrderNo());
		invMoveTask.setInvMoveNo(invMove.getInvMoveNo());
		invMoveTask.setMoveUserId(invMoveTaskFormVo.getMoveUserId());
		invMoveTask.setMoveUser(invMoveTaskFormVo.getMoveUser());
		invMoveTask.setWarehouseId(invMove.getWarehouseId());
		invMoveTask.setStatus(InvMoveTaskStatus.PENDING_DEAL);
		this.save(invMoveTask);
	}

	@Override
	public void updateInvMoveItem(List<InvMoveItem> invMoveItemListVo) {
		Long invMoveId = null;
		int differenceCount = 0;
		for(InvMoveItem invMoveItem : invMoveItemListVo) {
			invMoveId = invMoveItem.getInvMoveId();
			invMoveItemService.updateById(invMoveItem);

			if(invMoveItem.getDifferenceCount() != 0) {
				differenceCount++;
			}
		}

		InvMoveTask invMoveTask = this.getOne(new LambdaQueryWrapper<InvMoveTask>().eq(InvMoveTask::getInvMoveId, invMoveId));
		invMoveTask.setMoveCompleteTime(new Date());
		invMoveTask.setStatus(InvMoveTaskStatus.FINISH);
		this.updateById(invMoveTask);

		InvMove invMove = invMoveService.getById(invMoveId);
		invMove.setStatus(InvMoveStatus.FINISH);
		invMoveService.updateById(invMove);

		//更新库存
		if(differenceCount > 0) {
			List<InvMoveItem> invMoveItemList = invMoveItemService.list(new LambdaQueryWrapper<InvMoveItem>().eq(InvMoveItem::getInvMoveId, invMoveTask.getInvMoveId()));
			List<InventoryInfoVo> inventoryInfoVoList = new ArrayList<>();
			for(InvMoveItem invMoveItem : invMoveItemList) {
				if(invMoveItem.getDifferenceCount() == 0) continue;
				//盘点属于更新库存
				InventoryInfoVo inventoryInfoVo = new InventoryInfoVo();
				inventoryInfoVo.setGoodsId(invMoveItem.getGoodsId());
				inventoryInfoVo.setWarehouseId(invMove.getWarehouseId());
				inventoryInfoVo.setPutawayCount(invMoveItem.getDifferenceCount());
				inventoryInfoVoList.add(inventoryInfoVo);
			}
			if(!CollectionUtils.isEmpty(inventoryInfoVoList)) {
				Boolean isPutaway = inventoryInfoService.countingSyncInventory(invMoveTask.getTaskNo(), inventoryInfoVoList, InvLogType.INV_MOVE);
				if(!isPutaway) {
					throw new WmsException(ResultCodeEnum.DATA_ERROR);
				}
			}
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void syncInventory(Long id) {
//		InvMoveTask invMoveTask = this.getById(id);
//		invMoveTask.setStatus(InvMoveTaskStatus.FINISH);
//		this.updateById(invMoveTask);
//
//		InvMove invMove = invMoveService.getById(invMoveTask.getInvMoveId());
//		invMove.setStatus(InvMoveStatus.FINISH);
//		invMoveService.updateById(invMove);
//
//		List<InvMoveItem> invMoveItemList = invMoveItemService.list(new LambdaQueryWrapper<InvMoveItem>().eq(InvMoveItem::getInvMoveId, invMoveTask.getInvMoveId()));
//		List<InventoryInfoVo> inventoryInfoVoList = new ArrayList<>();
//		for(InvMoveItem invMoveItem : invMoveItemList) {
//			if(invMoveItem.getDifferenceCount() == 0) continue;
//			//盘点属于更新库存
//			InventoryInfoVo inventoryInfoVo = new InventoryInfoVo();
//			inventoryInfoVo.setGoodsId(invMoveItem.getGoodsId());
//			inventoryInfoVo.setWarehouseId(invMove.getWarehouseId());
//			inventoryInfoVo.setPutawayCount(invMoveItem.getDifferenceCount());
//			inventoryInfoVoList.add(inventoryInfoVo);
//		}
//		if(!CollectionUtils.isEmpty(inventoryInfoVoList)) {
//			Boolean isPutaway = inventoryInfoService.countingSyncInventory(invMoveTask.getTaskNo(), inventoryInfoVoList, InvLogType.INV_MOVE);
//			if(!isPutaway) {
//				throw new WmsException(ResultCodeEnum.DATA_ERROR);
//			}
//		}
	}

}
