package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.GoodsInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qy
 *
 */
@Api(value = "GoodsInfo管理", tags = "GoodsInfo管理")
@RestController
@RequestMapping(value="/admin/base/goodsInfo")
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


	@ApiOperation(value = "获取分页列表")
	@PostMapping("findPage/{page}/{limit}")
	public PageVo<GoodsInfo> findPage(
			@ApiParam(name = "page", value = "当前页码", required = true)
			@PathVariable Long page,

			@ApiParam(name = "limit", value = "每页记录数", required = true)
			@PathVariable Long limit,

			@ApiParam(name = "goodsInfoVo", value = "查询对象", required = false)
					@RequestBody GoodsInfoQueryVo goodsInfoQueryVo) {
		Page<GoodsInfo> pageParam = new Page<>(page, limit);
		//IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
		return new PageVo<>(null);
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
}

