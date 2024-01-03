package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.util.DateUtil;
import com.atguigu.wms.inventory.service.InvStatisticsService;
import com.atguigu.wms.model.inventory.InvStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Dunston
 *
 */
@Api(value = "InvStatistics管理", tags = "InvStatistics管理")
@RestController
@RequestMapping(value="/admin/inventory/invStatistics")
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvStatisticsController {
	
	@Resource
	private InvStatisticsService invStatisticsService;

	@ApiOperation(value = "获取")
	@GetMapping("get/{type}")
	public Result get(@PathVariable String type) {
		return Result.ok(invStatisticsService.getByTpye(type));
	}

	@ApiOperation(value = "初始化数据")
	@GetMapping("init/{id}")
	public Result init(@PathVariable Long id) {
		DateTime dateTime = new DateTime();
		int dayOfYear = dateTime.getDayOfYear();
		Date startDate = dateTime.plusDays(1-dayOfYear).toDate();
		Date endDate = dateTime.plusDays(1-dayOfYear).plusYears(2).toDate();

		Random random = new Random();
		while (DateUtil.dateCompare(startDate, endDate)) {
			InvStatistics invStatistics = new InvStatistics();
			invStatistics.setDay(startDate);
			invStatistics.setInCount(random.nextInt(1000));
			invStatistics.setOutCount(random.nextInt(1000));
			invStatistics.setWarehouseId(id);
			invStatisticsService.save(invStatistics);

			startDate = new DateTime(startDate).plusDays(1).toDate();
		}
		return Result.ok();
	}
}

