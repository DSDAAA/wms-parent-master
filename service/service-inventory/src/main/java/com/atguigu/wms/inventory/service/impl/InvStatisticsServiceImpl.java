package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.common.util.DateUtil;
import com.atguigu.wms.inventory.mapper.InvStatisticsMapper;
import com.atguigu.wms.inventory.service.InvStatisticsService;
import com.atguigu.wms.model.inventory.InvStatistics;
import com.atguigu.wms.model.outbound.OutOrderItem;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvStatisticsServiceImpl extends ServiceImpl<InvStatisticsMapper, InvStatistics> implements InvStatisticsService {

	@Resource
	private InvStatisticsMapper invStatisticsMapper;

	@Override
	public Map<String, Object> getByTpye(String type) {
		List<String> xDate = new ArrayList<>();
		List<Integer> yDate1 = new ArrayList<>();
		List<Integer> yDate2 = new ArrayList<>();

		DateTime dateTime = DateTime.parse(new DateTime().toString("yyyy-MM-dd"));
		Date endDate = dateTime.toDate();
		Date startDate = null;
		if("week".equals(type)) {
			int dayOfWeek = dateTime.getDayOfWeek();
			startDate = dateTime.plusDays(1-dayOfWeek).toDate();
		} else if("month".equals(type)) {
			int dayOfMonth = dateTime.getDayOfMonth();
			startDate = dateTime.plusDays(1-dayOfMonth).toDate();
		} else {
			int dayOfYear = dateTime.getDayOfYear();
			startDate = dateTime.plusDays(1-dayOfYear).toDate();
		}
		LambdaQueryWrapper<InvStatistics> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.between(InvStatistics::getDay, startDate, endDate);
		if(null != AuthContextHolder.getWarehouseId()) {
			queryWrapper.eq(InvStatistics::getWarehouseId,AuthContextHolder.getWarehouseId());
		}
		List<InvStatistics> invStatisticsList = invStatisticsMapper.selectList(queryWrapper);

		Map<Date, List<InvStatistics>> dayToInvStatisticsListMap = invStatisticsList.stream().collect(Collectors.groupingBy(InvStatistics::getDay));
		Iterator<Map.Entry<Date, List<InvStatistics>>> iterator = dayToInvStatisticsListMap.entrySet().iterator();
		//先按天汇总，每个仓库一天一条统计数据
		List<InvStatistics> invStatisticsByDayList = new ArrayList<>();
		while (iterator.hasNext()) {
			Map.Entry<Date, List<InvStatistics>> entry = iterator.next();
			Date key = entry.getKey();
			List<InvStatistics> list = entry.getValue();
			Integer totalInCount = list.stream().map(InvStatistics::getInCount).reduce((a, b) -> a + b).get();
			Integer totalOutCount = list.stream().map(InvStatistics::getOutCount).reduce((a, b) -> a + b).get();

			InvStatistics invStatistics = new InvStatistics();
			invStatistics.setInCount(totalInCount);
			invStatistics.setOutCount(totalOutCount);
			invStatistics.setDay(key);
			invStatisticsByDayList.add(invStatistics);
		}

		if("week".equals(type)) {
			xDate.add("周一");
			xDate.add("周二");
			xDate.add("周三");
			xDate.add("周四");
			xDate.add("周五");
			xDate.add("周六");
			xDate.add("周日");

			Map<Date, InvStatistics> dayToInvStatisticsMap = invStatisticsByDayList.stream().collect(Collectors.toMap(InvStatistics::getDay, InvStatistics -> InvStatistics));

			while (DateUtil.dateCompare(startDate, endDate)) {
				InvStatistics invStatistics = dayToInvStatisticsMap.get(startDate);
				if(null != invStatistics) {
					yDate1.add(invStatistics.getInCount());
					yDate2.add(invStatistics.getOutCount());
				} else {
					yDate1.add(0);
					yDate2.add(0);
				}
				startDate = new DateTime(startDate).plusDays(1).toDate();
			}
		}else if("month".equals(type)) {
			DateTime lastDate = dateTime.dayOfMonth().withMaximumValue();
			int lastDay = lastDate.getDayOfMonth();
			for(int i=1; i<=lastDay; i++) {
				xDate.add(i+"号");
			}
			Map<Date, InvStatistics> dayToInvStatisticsMap = invStatisticsByDayList.stream().collect(Collectors.toMap(InvStatistics::getDay, InvStatistics -> InvStatistics));
			while (DateUtil.dateCompare(startDate, endDate)) {
				InvStatistics invStatistics = dayToInvStatisticsMap.get(startDate);
				if(null != invStatistics) {
					yDate1.add(invStatistics.getInCount());
					yDate2.add(invStatistics.getOutCount());
				} else {
					yDate1.add(0);
					yDate2.add(0);
				}

				startDate = new DateTime(startDate).plusDays(1).toDate();
			}
		} else {
			for(int i=1; i<=12; i++) {
				xDate.add(i+"月");
			}

			Map<String, List<InvStatistics>> collect = invStatisticsByDayList.stream()
					.map(item -> {
						item.setMonth(new DateTime(item.getDay()).toString("yyyy-MM"));
						return item;
					})
					.collect(Collectors.groupingBy(InvStatistics::getMonth));
			Iterator<Map.Entry<String, List<InvStatistics>>> yearIterator = collect.entrySet().iterator();
			Map<String, Integer> inMonth = new HashMap<>();
			Map<String, Integer> outMonth = new HashMap<>();
			while (yearIterator.hasNext()) {
				Map.Entry<String, List<InvStatistics>> yearEntry = yearIterator.next();
				String key = yearEntry.getKey();
				List<InvStatistics> invStatisticsList1 = yearEntry.getValue();
				Integer totalInCount = invStatisticsList1.stream().map(InvStatistics::getInCount).reduce((a, b) -> a + b).get();
				Integer totalOutCount = invStatisticsList1.stream().map(InvStatistics::getOutCount).reduce((a, b) -> a + b).get();
				inMonth.put(key, totalInCount);
				outMonth.put(key, totalOutCount);
			}

			while (DateUtil.dateCompare(startDate, endDate)) {
				String month = new DateTime(startDate).toString("yyyy-MM");
				yDate1.add(inMonth.get(month) == null ? 0 :inMonth.get(month));
				yDate2.add(outMonth.get(month) == null ? 0 : outMonth.get(month));

				startDate = new DateTime(startDate).plusMonths(1).toDate();
			}
		}
		Map map = new HashMap();
		map.put("xDate", xDate);
		map.put("yDate1", yDate1);
		map.put("yDate2", yDate2);
		return map;
	}
}
