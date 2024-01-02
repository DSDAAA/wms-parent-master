package com.atguigu.wms.inventory.controller;

import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.inventory.service.InvMoveService;
import com.atguigu.wms.model.inventory.InvMove;
import com.atguigu.wms.vo.inventory.InvMoveQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author qy
 *
 */
@Api(value = "InvMove管理", tags = "InvMove管理")
@RestController
@RequestMapping(value="/admin/inventory/count")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CountController {
	
	@Resource
	private InvMoveService invMoveService;

	@ApiOperation(value = "获取")
	@GetMapping("{type}")
	public Result get(@PathVariable String type) {
		List<String> xDate = new ArrayList<>();
		List<Integer> yDate1 = new ArrayList<>();
		List<Integer> yDate2 = new ArrayList<>();
		Random random = new Random();
		if("week".equals(type)) {
			xDate.add("周一");
			xDate.add("周二");
			xDate.add("周三");
			xDate.add("周四");
			xDate.add("周五");
			xDate.add("周六");
			xDate.add("周日");
			DateTime dateTime = new DateTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int dayOfWeek = dateTime.getDayOfWeek();
			for(int i=1; i<=dayOfWeek; i++) {
				yDate1.add(random.nextInt(10000));
				yDate2.add(random.nextInt(10000));
			}
			for(int i=dayOfWeek; i<=7; i++) {
				yDate1.add(0);
				yDate2.add(0);
			}
		}else if("month".equals(type)) {
			DateTime dateTime = new DateTime();
			int dayOfMonth = dateTime.getDayOfMonth();
			DateTime lastDate = dateTime.dayOfMonth().withMaximumValue();
			int lastDay = lastDate.getDayOfMonth();
			for(int i=1; i<=lastDay; i++) {
				xDate.add(i+"号");
			}
			for(int i=1; i<=dayOfMonth; i++) {
				yDate1.add(random.nextInt(10000));
				yDate2.add(random.nextInt(10000));
			}
			for(int i=dayOfMonth; i<=lastDay; i++) {
				yDate1.add(0);
				yDate2.add(0);
			}
		} else {
			DateTime dateTime = new DateTime();
			int monthOfYear = dateTime.getMonthOfYear();
			for(int i=1; i<=12; i++) {
				xDate.add(i+"月");
			}
			for(int i=1; i<=monthOfYear; i++) {
				yDate1.add(random.nextInt(10000));
				yDate2.add(random.nextInt(10000));
			}
			for(int i=monthOfYear; i<=12; i++) {
				yDate1.add(0);
				yDate2.add(0);
			}
		}
		Map map = new HashMap();
		map.put("xDate", xDate);
		map.put("yDate1", yDate1);
		map.put("yDate2", yDate2);
		return Result.ok(map);
	}

}

