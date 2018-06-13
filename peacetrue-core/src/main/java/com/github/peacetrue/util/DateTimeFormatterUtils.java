package com.github.peacetrue.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * a util class for {@link java.time.format.DateTimeFormatter}
 *
 * @author xiayx
 * @see java.time.format.DateTimeFormatter
 */
public abstract class DateTimeFormatterUtils {

    /** formatter: MMdd */
    public static final DateTimeFormatter SHORT_MONTH_DAY = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.MONTH_OF_YEAR, 2).appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter();

    /** formatter: yyyyMMdd */
    public static final DateTimeFormatter SHORT_DATE = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4).append(SHORT_MONTH_DAY).toFormatter();

}
