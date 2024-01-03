package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qy
 */
//@CrossOrigin //跨域
@Api(tags = "数据字典管理")
@RestController
@RequestMapping(value = "/admin/base/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据id获取名称")
    @GetMapping("getNameById/{id}")
    public String importData(@PathVariable Long id) {
        return dictService.getNameById(id);
    }

    /**
     * 根据id获取子节点数据列表
     *
     * @param parentId
     * @return
     * @actor Dunston
     */
    @ApiOperation(value = "根据id获取子节点数据列表")
    @GetMapping("findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Integer parentId) {
        List<Dict> retList = dictService.findByParentId(parentId);
        return Result.ok(retList);
    }

    /**
     * 根据数据字典数据编码找到下面所有的子节点
     *
     * @param dictCode
     * @return
     */
    @ApiOperation(value = "根据数据字典数据编码找到下面所有的子节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> retList = dictService.findByDictCode(dictCode);
        return Result.ok(retList);
    }

//    @ApiOperation(value = "获取数据字典名称")
//    @GetMapping(value = "/getName/{parentDictCode}/{value}")
//    public String getName(
//            @ApiParam(name = "parentDictCode", value = "上级编码", required = true)
//            @PathVariable("parentDictCode") String parentDictCode,
//
//            @ApiParam(name = "value", value = "值", required = true)
//            @PathVariable("value") String value) {
//        return dictService.getNameByParentDictCodeAndValue(parentDictCode, value);
//    }

//    @ApiOperation(value = "获取数据字典名称")
//    @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Long", paramType = "path")
//    @GetMapping(value = "/getName/{value}")
//    public String getName(
//            @ApiParam(name = "value", value = "值", required = true)
//            @PathVariable("value") String value) {
//        return dictService.getNameByParentDictCodeAndValue("", value);
//    }
}

