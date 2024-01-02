package com.atguigu.wms.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期操作工具类
 *
 * @author qy
 * @since 1.0
 */
public class DateUtil {


    /**
     * 截取比较断两个日期对象的field处的值 。
     * 如果第一个日期小于、等于、大于第二个，则对应返回负整数、0、正整数
     *
     * @param date1 第一个日期对象，非null
     * @param date2 第二个日期对象，非null
     * @param field Calendar中的阈值
     *              <p>
     *              date1 > date2  返回：1
     *              date1 = date2  返回：0
     *              date1 < date2  返回：-1
     */
    public static int truncatedCompareTo(final Date date1, final Date date2, final int field) {
        return DateUtils.truncatedCompareTo(date1, date2, field);
    }

    /**
     * 比对时间大小 <=
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean dateCompare(Date beginDate, Date endDate) {
        if(null == beginDate || null == endDate) return false;
        if (DateUtil.truncatedCompareTo(beginDate, endDate, Calendar.SECOND) == 1) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws ParseException {
//        Date d = DateUtil.parseDate("2021-01-01");
//        Date d1 = DateUtil.parseDate("2010-01-02");
//        Date d2 = DateUtil.parseDate("2012-01-03");
//
//        System.out.println(DateUtil.dateForWeek(d1));
    }


}
