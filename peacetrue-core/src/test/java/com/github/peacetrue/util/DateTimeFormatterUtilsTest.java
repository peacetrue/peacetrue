package com.github.peacetrue.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

/**
 * @author : xiayx
 * @since : 2020-06-08 06:26
 **/
public class DateTimeFormatterUtilsTest {

    @Test
    public void year() {
        Year temporal = Year.parse("2020", DateTimeFormatterUtils.YEAR);
        System.out.println(temporal.format(DateTimeFormatterUtils.YEAR));
    }

    @Test
    public void shortMonth() {
        YearMonth temporal = YearMonth.parse("202001", DateTimeFormatterUtils.SHORT_MONTH);
        System.out.println(temporal.format(DateTimeFormatterUtils.SHORT_MONTH));
    }

    @Test
    public void commonTime() {
        LocalDateTime temporal = LocalDateTime.parse("2020-01-01 01:01:01", DateTimeFormatterUtils.COMMON_DATE_TIME);
        System.out.println(temporal.format(DateTimeFormatterUtils.COMMON_DATE));
    }
}
