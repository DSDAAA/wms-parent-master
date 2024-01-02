package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.service.ShipperInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.Dict;
import com.atguigu.wms.model.base.ShipperInfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qy
 */
//@CrossOrigin //跨域
@Api(tags = "数据字典管理")
@RestController
@RequestMapping(value = "/admin/base/shipperInfo")
public class ShipperInfoController {

    @Autowired
    private ShipperInfoService shipperInfoService;

    /**
     * 分页查询货主列表信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "分页查询货主列表信息")
    @GetMapping("{page}/{limit}")
    public Result shipperInfo(@PathVariable Long id) {

        return Result.ok();
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
}

