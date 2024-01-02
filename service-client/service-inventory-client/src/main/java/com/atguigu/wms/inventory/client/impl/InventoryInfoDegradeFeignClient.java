package com.atguigu.wms.inventory.client.impl;

import com.atguigu.wms.inventory.client.InventoryInfoFeignClient;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.inventory.InventoryInfoVo;
import com.atguigu.wms.vo.outbound.OrderLockVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InventoryInfoDegradeFeignClient implements InventoryInfoFeignClient {


    @Override
    public Boolean syncInventory(String inOrderNo, List<InventoryInfoVo> inventoryInfoVoList) {
        return false;
    }

    @Override
    public Map<Long, List<Long>> checkAndLockInventory(OrderLockVo orderLockVo) {
        return null;
    }

    @Override
    public InventoryInfo getByGoodsIdAndWarehouseId(Long goodsId, Long warehouseId) {
        return null;
    }
}

