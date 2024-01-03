package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.GoodsInfoDegradeFeignClient;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员用户API接口
 * </p>
 *
 * @author Dunston
 */
@FeignClient(value = "service-base", fallback = GoodsInfoDegradeFeignClient.class)
public interface GoodsInfoFeignClient {


    @ApiOperation(value = "获取分页列表")
    @PostMapping("/admin/base/goodsInfo/findPage/{page}/{limit}")
    PageVo<GoodsInfo> findPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody GoodsInfoQueryVo goodsInfoQueryVo);

    @ApiOperation(value = "获取列表")
    @PostMapping("/admin/base/goodsInfo/findList")
    List<GoodsInfo> findList(@RequestBody List<Long> idList);

    @ApiOperation(value = "获取")
    @GetMapping("/admin/base/goodsInfo/getGoodsInfo/{id}")
    GoodsInfo getGoodsInfo(@PathVariable Long id);

    @ApiOperation(value = "获取")
    @GetMapping("/admin/base/goodsInfo/getGoodsInfoBySkuId/{skuId}")
    GoodsInfo getGoodsInfoBySkuId(@PathVariable Long skuId);

}
