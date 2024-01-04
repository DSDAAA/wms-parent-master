package com.atguigu.wms.inbound.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.model.inbound.InApproveTask;
import com.atguigu.wms.vo.inbound.InApproveFormVo;
import com.atguigu.wms.vo.inbound.InApproveTaskQueryVo;
import com.atguigu.wms.inbound.service.InApproveTaskService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author Dunston
 *
 */
@Api(value = "InApproveTask管理", tags = "InApproveTask管理")
@RestController
@RequestMapping(value="/admin/inbound/inApproveTask")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InApproveTaskController {

	@Resource
	private InApproveTaskService inApproveTaskService;
}

