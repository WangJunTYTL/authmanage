package com.peaceful.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtils {
    public static final SimpleDateFormat SIMPLE_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 获取某个日期的 开始日期
     * 例子 2008-08-08 12:12:12 返回 2008-08-08 00:00:00.000
     * @return
     */
    public static Date getBeginOfOneday(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    public static List<String> getDateRangeBetweenBy(String startTimeString, String endTimeString) {
        Date startTime = DateUtils.parseDate(startTimeString);
        Date endTime = DateUtils.parseDate(endTimeString);
        Date afterDay = startTime;

        List<String> dateList = new ArrayList<String>();
        dateList.add(startTimeString);
        while(afterDay.getTime() < endTime.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(afterDay);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            afterDay.setTime(c.getTime().getTime());
            dateList.add(DateUtils.formatDate(afterDay));
        }

        return dateList;
    }

    public static List<Date> getDateRangeBetweenBy(Date startTime, Date endTime) {
        Date afterDay = startTime;

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(startTime);
        while(afterDay.getTime() < endTime.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(afterDay);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            afterDay.setTime(c.getTime().getTime());
            dateList.add(afterDay);
        }

        return dateList;
    }

    public static List<String> getDateRangeBetweenDays(int days) {
        Date initialTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(initialTime);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - days + 1);
        initialTime.setTime(c.getTime().getTime());
        List<String> dateList = new ArrayList<String>();
        dateList.add(DateUtils.formatDate(initialTime));

        Date afterDay = initialTime;
        int step = 0;
        while(step < days - 1) {
            c.setTime(afterDay);
            day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            afterDay.setTime(c.getTime().getTime());
            dateList.add(DateUtils.formatDate(afterDay));
            step++;
        }

        return dateList;
    }

    /**
     * 获取某个日期的 结束时间
     * 例子 2008-08-08 12:12:12 返回 2008-08-08 23:59:59.999
     * @return
     */
    public static Date getEndOfDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,999);
        return cal.getTime();
    }

    /**
     * 获取当前时间 前/后 N/年/月/天/小时。。。的时间
     * @return
     */
    public static Date getDateByAdd(Date d,int type,int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(type,num);
        return cal.getTime();
    }

    /**
     * 获取当前日期的 前/后 天
     * @param day
     * @return 返回字符串日期，格式：yyyy-MM-dd
     */
    public static Date getAddDayDate(Date date, int day) {
        Date d = getDateByAdd(date, Calendar.DAY_OF_MONTH, day);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return parseDate(df.format(d));
    }

    /**
     * 获取当前日期的 前/后 天
     * @param day
     * @return 返回字符串日期，格式：yyyy-MM-dd
     */
    public static String getAddDay(Date date, int day) {
        Date d = getDateByAdd(date, Calendar.DAY_OF_MONTH, day);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(d);
    }

    /**
     * 按照pattern转换日期为字符串
     * @param d
     * @param pattern
     * @return
     */
    public static String getStringByPattern(Date d,String pattern){
        if(d == null){
            return null;
        }
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(d);
    }
    /**
     * 按照pattern转换字符串为日期
     * @param str
     * @param pattern
     * @return
     */
    public static Date getDateByPattern(String str,String pattern){
        if(str == null || str.trim().length() == 0){
            return null;
        }
        DateFormat format = new SimpleDateFormat(pattern);
        try{
            return  format.parse(str);
        }catch(ParseException e){
            LOGGER.error("getDateByPattern:{}",e);
        }
        return null;
    }

    public static String getStringByPattern (String str,String pattern,String pattern1) {
        return getStringByPattern(getDateByPattern(str,pattern),pattern1);
    }

    /**
     * 获得某个月最大天数
     * @param d
     * @return
     */
    public static int getDaysInMonth(Date d){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.getActualMaximum(Calendar.DATE);
    }

    public static String formatDateTime(Date date){
        return SIMPLE_DATETIME_FORMAT.format(date);
    }

    public static String formatDate(Date date){
        return SIMPLE_DATE_FORMAT.format(date);
    }

    public static Date parseDate(String dateString) {
        Date date = new Date();
        try {
            date = SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("parseDate:{}",e);
        }
        return date;
    }

    public static Date parseDateTime(String dateString) {
        Date date = new Date();
        try {
            date = SIMPLE_DATETIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("parseDateTime:{}",e);
        }
        return date;
    }

}
