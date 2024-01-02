package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.model.base.StoreareaInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "StoreareaInfo管理", tags = "StoreareaInfo管理")
@RestController
@RequestMapping(value="/admin/base/storeareaInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreareaInfoController {

	@Resource
	private StoreareaInfoService storeareaInfoService;







	@ApiOperation(value = "获取对象")
	@GetMapping("getStoreareaInfo/{id}")
	public StoreareaInfo getStoreareaInfo(@PathVariable Long id) {
		return storeareaInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return storeareaInfoService.getNameById(id);
	}
}

