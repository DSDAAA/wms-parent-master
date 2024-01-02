package com.atguigu.wms.inventory.mapper;

import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.inventory.InventoryInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InventoryInfoMapper extends BaseMapper<InventoryInfo> {

    IPage<InventoryInfo> selectPage(Page<InventoryInfo> page, @Param("vo") InventoryInfoQueryVo inventoryInfoQueryVo);

    Integer putaway(@Param("warehouseId") Long warehouseId,@Param("goodsId") Long goodsId, @Param("count") Integer count);

    InventoryInfo getCheck(@Param("warehouseId") Long warehouseId,@Param("goodsId") Long goodsId);

    List<InventoryInfo> check(@Param("goodsId") Long goodsId, @Param("count") Integer count);

    Integer lock(@Param("warehouseId") Long warehouseId,@Param("goodsId") Long goodsId, @Param("count")Integer count);

    Integer unLock(@Param("warehouseId") Long warehouseId,@Param("goodsId") Long goodsId, @Param("count")Integer count);

    Integer minus(@Param("warehouseId") Long warehouseId,@Param("goodsId") Long goodsId, @Param("count")Integer count);

    Integer updatePicking(@Param("warehouseId") Long warehouseId,@Param("goodsId") Long goodsId, @Param("count")Integer count);
}
