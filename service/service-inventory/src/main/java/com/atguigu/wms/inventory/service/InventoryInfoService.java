package com.atguigu.wms.inventory.service;

import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.enums.InvLogType;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.model.outbound.OutPickingItem;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.vo.inventory.InventoryFormVo;
import com.atguigu.wms.vo.inventory.InventoryInfoQueryVo;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import com.atguigu.wms.vo.outbound.OrderResponseVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface InventoryInfoService extends IService<InventoryInfo> {

    PageVo<GoodsInfo> selectPage(Page<GoodsInfo> pageParam, GoodsInfoQueryVo goodsInfoQueryVo);

    Boolean syncInventory(String inOrderNo, List<InventoryInfoVo> inventoryInfoVoList, InvLogType invLogType);

    Boolean countingSyncInventory(String taskNo, List<InventoryInfoVo> inventoryInfoVoList, InvLogType invLogType);

    InventoryInfo getByGoodsIdAndWarehouseId(Long goodsId, Long warehouseId);

    List<InventoryInfo> findByStorehouseId(Long storehouseId);

    /**
     *
     * @param orderLockVo
     * @return
     */
    Result<List<OrderResponseVo>> checkAndLock(OrderLockVo orderLockVo);

    void unlock(String orderNo);

    void minus(String outOrderNo);

    Integer checkInventory(Long skuId);

    void updateInventory(InventoryFormVo inventoryFormVo);

    void picking(List<OutPickingItem> outPickingItemList);
}
