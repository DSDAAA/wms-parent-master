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
 * @author qy
 */
@Api(value = "WarehouseInfo管理", tags = "WarehouseInfo管理")
@RestController
@RequestMapping(value = "/admin/base/warehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class WarehouseInfoController {

    @Resource
    private WarehouseInfoService warehouseInfoService;


    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        warehouseInfoService.removeByIds(idList);
        return Result.ok();
    }


    @ApiOperation(value = "查询库位节点")
    @GetMapping("findNodes")
    public Result findNodes() {
        return Result.ok(warehouseInfoService.findNodes());
    }

    @ApiOperation(value = "获取对象")
    @GetMapping("getWarehouseInfo/{id}")
    public WarehouseInfo getWarehouseInfo(@PathVariable Long id) {
        return warehouseInfoService.getById(id);
    }

    @ApiOperation(value = "获取名称")
    @GetMapping("getNameById/{id}")
    public String getNameById(@PathVariable Long id) {
        return warehouseInfoService.getNameById(id);
    }

    @PostMapping("findNameByIdList")
    public List<String> findNameByIdList(@RequestBody List<Long> idList) {
        return warehouseInfoService.findNameByIdList(idList);
    }

    @ApiOperation(value = "根据用户地址给满足条件的仓库指定优先级")
    @PostMapping("findPriorityWarehouseIdList")
    public List<Long> findNameByIdList(@RequestBody OutOrderAddressVo outOrderAddressVo) {
        return warehouseInfoService.findPriorityWarehouseIdList(outOrderAddressVo);
    }

    /**
     * 分页查询仓库列表信息
     *
     * @param page
     * @param limit
     * @param warehouseInfoQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询仓库列表信息")
    @GetMapping("findPage/{page}/{limit}")
    public Result findPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "page", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "page", value = "查询对象", required = false)
            @PathVariable WarehouseInfoQueryVo warehouseInfoQueryVo) {
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
}

