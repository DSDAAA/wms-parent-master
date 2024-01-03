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
    @PostMapping("findPage/{page}/{limit}")
    public Result findPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "storeareaInfo", value = "查询对象", required = false)
            @RequestBody StoreshelfInfoQueryVo storeshelfInfoQueryVo) {
        Page<StoreshelfInfo> retPage = new Page<>(page, limit);
        IPage<StoreshelfInfo> pageModel = storeshelfInfoService.getPageList(retPage, storeshelfInfoQueryVo);
        //IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
        return Result.ok(pageModel);
    }
}

