package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.GoodsTypeService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.GoodsType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author qy
 */
@Api(value = "GoodsType管理", tags = "GoodsType管理")
@RestController
@RequestMapping(value = "/admin/base/goodsType")
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
     * 根据上级id获取子节点数据列表
     *
     * @param parentId
     * @return
     */
    @ApiOperation(value = "根据上级id获取子节点数据列表")
    @GetMapping("findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Integer parentId) {
        List<GoodsType> retList = goodsTypeService.findByParentId(parentId);
        return Result.ok(retList);
    }

    /**
     * 查询三级分类
     *
     * @return
     */
    @ApiOperation(value = "查询三级分类")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<GoodsType> retList = goodsTypeService.findNodes();
        return Result.ok(retList);
    }
}

