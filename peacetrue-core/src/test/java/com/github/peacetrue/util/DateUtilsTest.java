package com.github.peacetrue.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.peacetrue.util.DateUtils.toCalendar;

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
        Assertions.assertEquals(stringDateTime, dateTimeFormat.format(dateTime));
    }

    @Test
    public void toLocalDateTime() throws Exception {
        Date parse = dateTimeFormat.parse(stringDateTime);
        LocalDateTime localDateTime = DateUtils.toLocalDateTime(parse);
        Assertions.assertEquals(stringDateTime, localDateTime.format(DateUtils.COMMON_DATE_TIME));
    }

    @Test
    public void fromLocalDate() throws Exception {
        LocalDate localDate = LocalDate.parse(stringDate);
        Date dateTime = DateUtils.fromLocalDate(localDate);
        Assertions.assertEquals(stringDate, dateFormat.format(dateTime));
    }

    @Test
    public void toLocalDate() throws Exception {
        Date parse = dateFormat.parse(stringDate);
        LocalDate localDate = DateUtils.toLocalDate(parse);
        Assertions.assertEquals(stringDate, localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }


    @Test
    public void findValueBetweenRange() throws Exception {
        Date start = dateFormat.parse("2019-01-01");
        Date end = dateFormat.parse("2019-02-01");
        List<Date> values = DateUtils.findValueBetweenRange(toCalendar(start), toCalendar(end), Calendar.MONTH);
        System.out.println(values.stream().map(date -> dateFormat.format(date)).collect(Collectors.toList()));

    }
}
