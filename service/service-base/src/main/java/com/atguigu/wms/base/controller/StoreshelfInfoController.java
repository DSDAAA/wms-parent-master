package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.StoreshelfInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
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
@Api(value = "StoreshelfInfo管理", tags = "StoreshelfInfo管理")
@RestController
@RequestMapping(value = "/admin/base/storeshelfInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreshelfInfoController {

    @Resource
    private StoreshelfInfoService storeshelfInfoService;

    /**
     * 分页查询货架列表信息
     *
     * @param page
     * @param limit
     * @param storeshelfInfoQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询货架列表信息")
    @GetMapping("{page}/{limit}")
    public Result findPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "storeareaInfo", value = "查询对象", required = false)
            @RequestBody(required = false) StoreshelfInfoQueryVo storeshelfInfoQueryVo) {
        Page<StoreshelfInfo> retPage = new Page<>(page, limit);
        IPage<StoreshelfInfo> pageModel = storeshelfInfoService.getPageList(retPage, storeshelfInfoQueryVo);
        //IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 添加货架
     *
     * @param storeshelfInfo
     * @return
     */
    @ApiOperation(value = "添加货架")
    @PostMapping("save")
    public Result saveStoreshelf(
            @ApiParam(name = "storeshelfInfo", value = "货架对象")
            @RequestBody StoreshelfInfo storeshelfInfo) {
        storeshelfInfoService.saveStoreshelf(storeshelfInfo);
        return Result.ok();
    }

    /**
     * 根据货架id,查询货架信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据货架id,查询货架信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Integer id) {
        StoreshelfInfo ret = storeshelfInfoService.get(id);
        return Result.ok(ret);
    }

    /**
     * 修改货架信息
     *
     * @param storeshelfInfo
     * @return
     */
    @ApiOperation(value = "修改货架信息")
    @PutMapping("update")
    public Result update(@RequestBody StoreshelfInfo storeshelfInfo) {
        storeshelfInfoService.update(storeshelfInfo);
        return Result.ok();
    }

    /**
     * 删除货架信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除货架信息")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {
        storeshelfInfoService.remove(id);
        return Result.ok();
    }

    /**
     * 根据库区id,查询库区下的所有货架
     *
     * @param storeareaId
     * @return
     */
    @ApiOperation(value = "根据库区id,查询库区下的所有货架")
    @GetMapping("findByStoreareaId/{storeareaId}")
    public Result findByStoreareaId(@PathVariable Integer storeareaId) {
        List<StoreareaInfo> retList = storeshelfInfoService.findByStoreareaId(storeareaId);
        return Result.ok(retList);
    }
}

