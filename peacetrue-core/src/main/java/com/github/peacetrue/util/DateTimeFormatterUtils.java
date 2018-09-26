package com.github.peacetrue.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * a util class for {@link java.time.format.DateTimeFormatter}
 *
 * @author xiayx
 * @see java.time.format.DateTimeFormatter
 */
public abstract class DateTimeFormatterUtils {

    /** formatter: MMdd */
    public static DateTimeFormatter SHORT_MONTH_DAY = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.MONTH_OF_YEAR, 2).appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter();

    /** formatter: yyyyMMdd */
    public static DateTimeFormatter SHORT_DATE = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4).append(SHORT_MONTH_DAY).toFormatter();

    /** formatter: yyyy-MM-dd HH:mm:ss */
    public static DateTimeFormatter COMMON_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter(Locale.getDefault());


}
