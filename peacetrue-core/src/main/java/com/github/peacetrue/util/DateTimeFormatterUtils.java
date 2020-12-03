package com.github.peacetrue.util;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * a util class for {@link java.time.format.DateTimeFormatter}
 *
 * @author xiayx
 * @see java.time.format.DateTimeFormatter
 */
public abstract class DateTimeFormatterUtils {

    /** formatter: yyyy */
    public static DateTimeFormatter YEAR = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .toFormatter();

    /** formatter: MM */
    public static DateTimeFormatter MONTH = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .toFormatter();

    /** formatter: dd */
    public static DateTimeFormatter DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .toFormatter();

    /** formatter: yyyyMM */
    public static DateTimeFormatter SHORT_MONTH = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(YEAR)
            .append(MONTH)
            .toFormatter();


    /** formatter: MMdd */
    public static DateTimeFormatter SHORT_MONTH_DAY = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .toFormatter();

    /** formatter: yyyyMMdd */
    public static DateTimeFormatter SHORT_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.YEAR, 4)
            .append(SHORT_MONTH_DAY)
            .toFormatter();


    /** formatter: yyyy-MM */
    public static DateTimeFormatter COMMON_MONTH = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(YEAR)
            .appendLiteral('-')
            .append(MONTH)
            .toFormatter();


    /** formatter: yyyy-MM-dd */
    public static DateTimeFormatter COMMON_DATE = ISO_LOCAL_DATE;

    /** formatter: yyyy-MM-dd HH:mm:ss */
    public static DateTimeFormatter COMMON_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(COMMON_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter();

    /** formatter: yyyy/MM/dd */
    public static DateTimeFormatter SEPARATOR_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(YEAR)
            .appendLiteral(File.separatorChar)
            .append(MONTH)
            .appendLiteral(File.separatorChar)
            .append(DATE)
            .toFormatter();


}
