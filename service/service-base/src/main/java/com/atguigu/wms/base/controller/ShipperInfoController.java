package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.ShipperInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dunston
 */
//@CrossOrigin //跨域
@Api(tags = "ShipperInfo管理")
@RestController
@RequestMapping(value = "/admin/base/shipperInfo")
public class ShipperInfoController {

    @Autowired
    private ShipperInfoService shipperInfoService;

    /**
     * 分页查询货主列表信息
     *
     * @param page
     * @param limit
     * @param shipperInfoQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询货主列表信息")
    @GetMapping("{page}/{limit}")
    public Result getPageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "shipperInfoVo", value = "查询对象", required = false)
            @RequestBody(required = false) ShipperInfoQueryVo shipperInfoQueryVo) {
        Page<ShipperInfo> retPage = new Page<>(page, limit);
        IPage<ShipperInfo> pageModel = shipperInfoService.getPageList(retPage, shipperInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 新增货主
     *
     * @param shipperInfo
     * @return
     */
    @ApiOperation(value = "新增货主")
    @PostMapping("save")
    public Result save(@RequestBody ShipperInfo shipperInfo) {
        shipperInfoService.save(shipperInfo);
        return Result.ok();
    }

    /**
     * 根据货主id 查询货主信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据货主id 查询货主信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Integer id) {
        ShipperInfo ret = shipperInfoService.get(id);
        return Result.ok(ret);
    }

    /**
     * 修改货主
     *
     * @param shipperInfo
     * @return
     */
    @ApiOperation(value = "修改货主")
    @PutMapping("update")
    public Result update(@RequestBody ShipperInfo shipperInfo) {
        shipperInfoService.update(shipperInfo);
        return Result.ok();
    }

    /**
     * 删除货主
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除货主")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {
        shipperInfoService.remove(id);
        return Result.ok();
    }

    /**
     * 搜索货主名称
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "搜索货主名称")
    @GetMapping("findByKeyword/{keyword}")
    public Result findByKeyword(@PathVariable String keyword) {
        List<ShipperInfo> list = shipperInfoService.findByKeyword(keyword);
        return Result.ok(list);
    }
}

