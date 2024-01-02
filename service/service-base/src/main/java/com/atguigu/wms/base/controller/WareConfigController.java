package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.WareConfigService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.WareConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "WaveConfig管理", tags = "WaveConfig管理")
@RestController
@RequestMapping(value="/admin/base/wareConfig")
@SuppressWarnings({"unchecked", "rawtypes"})
public class WareConfigController {

	@Resource
	private WareConfigService waveConfigService;


	@ApiOperation(value = "获取")
	@GetMapping("get")
	public Result get() {
		return Result.ok(waveConfigService.getById(1L));
	}

	@ApiOperation(value = "修改")
	@PutMapping("update")
	public Result updateById(@RequestBody WareConfig wareConfig) {
		waveConfigService.updateById(wareConfig);
		return Result.ok();
	}

	@ApiOperation(value = "获取")
	@GetMapping("getWareConfig")
	public WareConfig getWareConfig() {
		return waveConfigService.getById(1L);
	}
}

