package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.GoodsInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
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
@Api(value = "GoodsInfo管理", tags = "GoodsInfo管理")
@RestController
@RequestMapping(value = "/admin/base/goodsInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsInfoController {

    @Resource
    private GoodsInfoService goodsInfoService;


    @ApiOperation(value = "根据关键字查看")
    @GetMapping("findByKeyword/{keyword}")
    public Result findByKeyword(@PathVariable String keyword) {
        return Result.ok(goodsInfoService.findByKeyword(keyword));
    }

    @ApiOperation(value = "根据第三级分类id获取货品三级分类id列表")
    @GetMapping("findGoodsTypeIdList/{goodsTypeId}")
    public Result findGoodsTypeIdList(@PathVariable Long goodsTypeId) {
        return Result.ok(goodsInfoService.findGoodsTypeIdList(goodsTypeId));
    }

    @ApiOperation(value = "获取列表")
    @PostMapping("findList")
    public List<GoodsInfo> findList(@RequestBody List<Long> idList) {
        return goodsInfoService.listByIds(idList);
    }

    /**
     * 获取分页列表
     *
     * @param page
     * @param limit
     * @param goodsInfoQueryVo
     * @return
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result getPageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "goodsInfoVo", value = "查询对象", required = false)
            @RequestBody(required = false) GoodsInfoQueryVo goodsInfoQueryVo) {
        Page<GoodsInfo> retPage = new Page<>(page, limit);
        IPage<GoodsInfo> pageModel = goodsInfoService.getPageList(retPage, goodsInfoQueryVo);
        //IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("getGoodsInfo/{id}")
    public GoodsInfo getGoodsInfo(@PathVariable Long id) {
        return goodsInfoService.getGoodsInfo(id);
    }

    @ApiOperation(value = "获取")
    @GetMapping("getGoodsInfoBySkuId/{skuId}")
    public GoodsInfo getGoodsInfoBySkuId(@PathVariable Long skuId) {
        return goodsInfoService.getGoodsInfoBySkuId(skuId);
    }

    /**
     * 新增货品
     *
     * @param goodsInfo
     * @return
     */
    @ApiOperation(value = "新增货品")
    @PostMapping("save")
    public Result save(
            @ApiParam(name = "goodsInfo", value = "商品信息", required = true)
            @RequestBody GoodsInfo goodsInfo) {
        goodsInfoService.save(goodsInfo);
        return Result.ok();
    }

    /**
     * 根据货品信息id，查询货品
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据货品信息id，查询货品")
    @GetMapping("get/{id}")
    public Result get(
            @ApiParam(name = "id", value = "商品id", required = true)
            @PathVariable Integer id) {
        GoodsInfo goodsInfo = goodsInfoService.get(id);
        return Result.ok(goodsInfo);
    }

    /**
     * 修改货品信息(货主)
     *
     * @param goodsInfo
     * @return
     */
    @ApiOperation(value = "修改货品信息")
    @PutMapping("update")
    public Result update(
            @ApiParam(name = "goodsInfo", value = "商品信息", required = true)
            @RequestBody GoodsInfo goodsInfo) {
        goodsInfoService.update(goodsInfo);
        return Result.ok();
    }

    /**
     * 删除货品信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除货品信息")
    @DeleteMapping("remove/{id}")
    public Result remove(
            @ApiParam(name = "id", value = "商品id", required = true)
            @PathVariable Integer id) {
        goodsInfoService.remove(id);
        return Result.ok();
    }

    /**
     * 启用/下线按钮
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "启用/下线按钮")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(
            @ApiParam(name = "id", value = "商品id", required = true)
            @PathVariable Integer id,
            @ApiParam(name = "status", value = "状态信息（码）", required = true)
            @PathVariable Integer status) {
        goodsInfoService.updateStatus(id, status);
        return Result.ok();
    }
}

