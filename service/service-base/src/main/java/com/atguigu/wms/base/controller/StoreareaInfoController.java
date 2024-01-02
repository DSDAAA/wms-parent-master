package com.atguigu.wms.base.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.base.service.StoreareaInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

