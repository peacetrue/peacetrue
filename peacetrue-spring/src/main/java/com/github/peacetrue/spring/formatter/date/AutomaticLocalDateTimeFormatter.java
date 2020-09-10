package com.github.peacetrue.spring.formatter.date;

import com.github.peacetrue.util.DateTimeFormatterUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 自动的日期转换器
 *
 * @author xiayx
 */
public class AutomaticLocalDateTimeFormatter extends AbstractAutomaticLocalDateFormatter<LocalDateTime> {

    public static final String DEFAULT_PADDING = " 00:00:00";

    public AutomaticLocalDateTimeFormatter() {
        super(DateTimeFormatterUtils.COMMON_DATE_TIME);
    }

    @Override
    protected LocalDateTime parseLong(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    @Override
    protected LocalDateTime parseString(DateTimeFormatter parser, String time) {
        //TODO 处理日期类型
        if (time.length() + DEFAULT_PADDING.length() == 19) {
            time += DEFAULT_PADDING;
        }
        return LocalDateTime.parse(time, parser);
    }

}
