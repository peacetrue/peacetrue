package com.github.peacetrue.spring.formatter.date;

import com.github.peacetrue.util.DateTimeFormatterUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 自动的日期转换器
 *
 * @author xiayx
 */
public class AutomaticLocalDateFormatter extends AbstractAutomaticLocalDateFormatter<LocalDate> {

    public AutomaticLocalDateFormatter() {
        super(DateTimeFormatterUtils.COMMON_DATE);
    }

    @Override
    protected LocalDate parseLong(Long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    protected LocalDate parseString(DateTimeFormatter parser, String time) {
        return LocalDate.parse(time, parser);
    }

}
