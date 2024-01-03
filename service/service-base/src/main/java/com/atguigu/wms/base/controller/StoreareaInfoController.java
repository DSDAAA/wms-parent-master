package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dunston
 */
@Api(value = "StoreareaInfo管理", tags = "StoreareaInfo管理")
@RestController
@RequestMapping(value = "/admin/base/storeareaInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreareaInfoController {

    @Resource
    private StoreareaInfoService storeareaInfoService;

    /**
     * 分页查询库区列表信息
     *
     * @param page
     * @param limit
     * @param storeareaInfoQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询库区列表信息")
    @PostMapping("findPage/{page}/{limit}")
    public Result findPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "storeareaInfo", value = "查询对象", required = false)
            @RequestBody StoreareaInfoQueryVo storeareaInfoQueryVo) {
        Page<StoreareaInfo> retPage = new Page<>(page, limit);
        IPage<StoreareaInfo> pageModel = storeareaInfoService.getPageList(retPage, storeareaInfoQueryVo);
        //IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 添加库区
     *
     * @param storeareaInfo
     * @return
     */
    @ApiOperation(value = "添加库区")
    @PostMapping("save")
    public Result saveWarehouse(
            @ApiParam(name = "warehouseInfo", value = "库区信息")
            @RequestBody StoreareaInfo storeareaInfo) {
        storeareaInfoService.saveWarehouse(storeareaInfo);
        return Result.ok();
    }

    /**
     * 根据库区id,查询库区信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据库区id,查询库区信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Integer id) {
        StoreareaInfo ret = storeareaInfoService.get(id);
        return Result.ok(ret);
    }

    /**
     * 修改库区
     *
     * @param storeareaInfo
     * @return
     */
    @ApiOperation(value = "修改库区")
    @PutMapping("update")
    public Result update(@RequestBody StoreareaInfo storeareaInfo) {
        storeareaInfoService.update(storeareaInfo);
        return Result.ok();
    }

    /**
     * 删除库区
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除库区")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {
        storeareaInfoService.remove(id);
        return Result.ok();
    }

    /**
     * 根据仓库id,查询仓库下的所有库区
     *
     * @param warehouseId
     * @return
     */
    @ApiOperation(value = "根据仓库id,查询仓库下的所有库区")
    @GetMapping("findByWarehouseId/{warehouseId}")
    public Result findByWarehouseId(@PathVariable Integer warehouseId) {
        List<StoreareaInfo> list = storeareaInfoService.findByWarehouseId(warehouseId);
        return Result.ok(list);
    }
}

