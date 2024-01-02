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
@RequestMapping(value = "/admin/base/shipperInfo")
public class ShipperInfoController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "分页查询货主列表信息")
    @GetMapping("{page}/{limit}}")
    public String shipperInfo(@PathVariable Long id) {
        return dictService.getNameById(id);
    }

}

