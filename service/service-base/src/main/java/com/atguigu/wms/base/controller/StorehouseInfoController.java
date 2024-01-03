package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.StorehouseInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
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
@Api(value = "StorehouseInfo管理", tags = "StorehouseInfo管理")
@RestController
@RequestMapping(value = "/admin/base/storehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StorehouseInfoController {

    @Resource
    private StorehouseInfoService storehouseInfoService;

    /**
     * 分页查询库位列表信息
     *
     * @param page
     * @param limit
     * @param storehouseInfoQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询库位列表信息")
    @PostMapping("findPage/{page}/{limit}")
    public Result findPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "storeareaInfo", value = "查询对象", required = false)
            @RequestBody StorehouseInfoQueryVo storehouseInfoQueryVo) {
        Page<StorehouseInfo> retPage = new Page<>(page, limit);
        IPage<StorehouseInfo> pageModel = storehouseInfoService.getPageList(retPage, storehouseInfoQueryVo);
        //IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 添加库位
     *
     * @param storehouseInfo
     * @return
     */
    @ApiOperation(value = "添加库位")
    @PostMapping("save")
    public Result saveStorehouse(
            @ApiParam(name = "storehouseInfo", value = "库位对象")
            @RequestBody StorehouseInfo storehouseInfo) {
        storehouseInfoService.saveStorehouse(storehouseInfo);
        return Result.ok();
    }

    /**
     * 根据库位id,查询库位信息"
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据库位id,查询库位信息")
    @GetMapping("getStorehouseInfo/{id}")
    public Result getStorehouseInfo(@PathVariable Integer id) {
        StorehouseInfo ret = storehouseInfoService.getStorehouseInfo(id);
        return Result.ok(ret);
    }

    /**
     * 修改库位信息
     *
     * @param storehouseInfo
     * @return
     */
    @ApiOperation(value = "修改库位信息")
    @PutMapping("update")
    public Result update(@RequestBody StorehouseInfo storehouseInfo) {
        storehouseInfoService.update(storehouseInfo);
        return Result.ok();
    }

    /**
     * 删除库位信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除库位信息")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {
        storehouseInfoService.remove(id);
        return Result.ok();
    }
}

