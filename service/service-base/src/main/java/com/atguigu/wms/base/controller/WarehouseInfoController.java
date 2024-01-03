package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dunston
 */
@Api(value = "WarehouseInfo管理", tags = "WarehouseInfo管理")
@RestController
@RequestMapping(value = "/admin/base/warehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class WarehouseInfoController {

    @Resource
    private WarehouseInfoService warehouseInfoService;

    /**
     * 分页查询仓库列表信息
     *
     * @param page
     * @param limit
     * @param warehouseInfoQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询仓库列表信息")
    @GetMapping("{page}/{limit}")
    public Result findPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "page", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "page", value = "查询对象", required = false)
            @PathVariable(required = false) WarehouseInfoQueryVo warehouseInfoQueryVo) {
        Page<WarehouseInfo> retPage = new Page<>(page, limit);
        IPage<WarehouseInfo> pageModel = warehouseInfoService.getPageList(retPage, warehouseInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 新增仓库
     *
     * @param warehouseInfo
     * @return
     */
    @ApiOperation(value = "新增仓库")
    @PostMapping("save")
    public Result save(
            @ApiParam(name = "warehouseInfo", value = "仓库信息")
            @RequestBody WarehouseInfo warehouseInfo) {
        warehouseInfoService.save(warehouseInfo);
        return Result.ok();
    }

    /**
     * 根据仓库id,查询仓库信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据仓库id,查询仓库信息")
    @GetMapping("get/{id}")
    public Result get(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable Integer id) {
        WarehouseInfo warehouseInfo = warehouseInfoService.get(id);
        return Result.ok(warehouseInfo);
    }

    /**
     * 修改仓库
     *
     * @param warehouseInfo
     * @return
     */
    @ApiOperation(value = "修改仓库")
    @PutMapping("update")
    public Result update(
            @ApiParam(name = "warehouseInfo", value = "仓库信息", required = true)
            @RequestBody WarehouseInfo warehouseInfo) {
        warehouseInfoService.update(warehouseInfo);
        return Result.ok();
    }

    /**
     * 删除仓库
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除仓库")
    @DeleteMapping("remove/{id}")
    public Result remove(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable Integer id) {
        warehouseInfoService.remove(id);
        return Result.ok();
    }

    /**
     * 查询所有仓库
     *
     * @return
     */
    @ApiOperation(value = "查询所有仓库")
    @DeleteMapping("findAll")
    public Result findAll() {
        List<WarehouseInfo> list = warehouseInfoService.findAll();
        return Result.ok(list);
    }
}

