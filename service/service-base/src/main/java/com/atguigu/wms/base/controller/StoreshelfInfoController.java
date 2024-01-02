package com.atguigu.wms.base.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.atguigu.wms.base.service.StoreshelfInfoService;
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
@Api(value = "StoreshelfInfo管理", tags = "StoreshelfInfo管理")
@RestController
@RequestMapping(value="/admin/base/storeshelfInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreshelfInfoController {

	@Resource
	private StoreshelfInfoService storeshelfInfoService;




	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		storeshelfInfoService.removeByIds(idList);
		return Result.ok();
	}


	@ApiOperation(value = "获取对象")
	@GetMapping("getStoreshelfInfo/{id}")
	public StoreshelfInfo getStoreshelfInfo(@PathVariable Long id) {
		return storeshelfInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return storeshelfInfoService.getNameById(id);
	}
}

