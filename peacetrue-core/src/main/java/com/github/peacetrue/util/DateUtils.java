package com.github.peacetrue.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * 日期工具类
 *
 * @author xiayx
 * @see Date
 * @see Calendar
 * @see LocalDate
 * @see LocalDateTime
 */
public abstract class DateUtils extends DateTimeFormatterUtils {

    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date fromCalendar(Calendar calendar) {
        return calendar.getTime();
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取起止范围之间的日期
     *
     * @param startInclusive 起始时间
     * @param endInclusive   结束时间
     * @param consumer       日期递增函数
     * @return 一个包含起止范围内所有日期的集合
     */
    public static List<Date> findValueBetweenRange(Calendar startInclusive, Calendar endInclusive, Consumer<Calendar> consumer) {
        return ComparableUtils.findValueBetweenRange(startInclusive, endInclusive, StreamUtils.fromConsumer(consumer), DateUtils::fromCalendar);
    }

    /** @see #findValueBetweenRange(Calendar, Calendar, Consumer) */
    public static List<Date> findValueBetweenRange(Calendar startInclusive, Calendar endInclusive, int timeUnit, int increase) {
        return findValueBetweenRange(startInclusive, endInclusive, calendar -> calendar.add(timeUnit, increase));
    }

    /** @see #findValueBetweenRange(Calendar, Calendar, int, int) */
    public static List<Date> findValueBetweenRange(Calendar startInclusive, Calendar endInclusive, int timeUnit) {
        return findValueBetweenRange(startInclusive, endInclusive, calendar -> calendar.add(timeUnit, 1));
    }

    /** @see #findValueBetweenRange(Calendar, Calendar, int) */
    public static List<Date> findValueBetweenRange(Calendar startInclusive, Calendar endInclusive) {
        return findValueBetweenRange(startInclusive, endInclusive, calendar -> calendar.add(Calendar.DATE, 1));
    }

}
