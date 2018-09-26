package com.github.peacetrue.util;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xiayx
 */
public class DateUtilsTest {

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String stringDateTime = "2000-06-15 12:30:30";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String stringDate = "2000-06-15";

    @Test
    public void fromLocalDateTime() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.parse(stringDateTime, DateUtils.COMMON_DATE_TIME);
        Date dateTime = DateUtils.fromLocalDateTime(localDateTime);
        Assert.assertEquals(stringDateTime, dateTimeFormat.format(dateTime));
    }

    @Test
    public void toLocalDateTime() throws Exception {
        Date parse = dateTimeFormat.parse(stringDateTime);
        LocalDateTime localDateTime = DateUtils.toLocalDateTime(parse);
        Assert.assertEquals(stringDateTime, localDateTime.format(DateUtils.COMMON_DATE_TIME));
    }

    @Test
    public void fromLocalDate() throws Exception {
        LocalDate localDate = LocalDate.parse(stringDate);
        Date dateTime = DateUtils.fromLocalDate(localDate);
        Assert.assertEquals(stringDate, dateFormat.format(dateTime));
    }

    @Test
    public void toLocalDate() throws Exception {
        Date parse = dateFormat.parse(stringDate);
        LocalDate localDate = DateUtils.toLocalDate(parse);
        Assert.assertEquals(stringDate, localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

}