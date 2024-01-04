package com.atguigu.wms.inbound.service.impl;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.NoUtils;
import com.atguigu.wms.enums.InTaskStatus;
import com.atguigu.wms.enums.InOrderStatus;
import com.atguigu.wms.inbound.service.InOrderService;
import com.atguigu.wms.inbound.service.InReceivingTaskService;
import com.atguigu.wms.model.inbound.InApproveTask;
import com.atguigu.wms.model.inbound.InOrder;
import com.atguigu.wms.model.inbound.InReceivingTask;
import com.atguigu.wms.vo.inbound.InApproveFormVo;
import com.atguigu.wms.vo.inbound.InApproveTaskQueryVo;
import com.atguigu.wms.inbound.mapper.InApproveTaskMapper;
import com.atguigu.wms.inbound.service.InApproveTaskService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InApproveTaskServiceImpl extends ServiceImpl<InApproveTaskMapper, InApproveTask> implements InApproveTaskService {

	@Resource
	private InApproveTaskMapper inApproveTaskMapper;

	@Resource
	private InOrderService inOrderService;

	@Resource
	private InReceivingTaskService inReceivingTaskService;

}
