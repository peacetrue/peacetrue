package com.github.peacetrue.spring.formatter.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * 自动的日期转换器
 *
 * @author xiayx
 */
public class AutomaticDateFormatter extends AbstractAutomaticDateFormatter<Date> {

    public AutomaticDateFormatter() {
        super("yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
    }

    public AutomaticDateFormatter(String... patterns) {
        super(patterns);
    }

    public AutomaticDateFormatter(String[] patterns, DateFormat format) {
        super(patterns, format);
    }

    @Override
    protected Date parseLong(Long time) {
        return new Date(time);
    }

    @Override
    protected Date parseString(DateFormat parse, String time) throws ParseException {
        return parse.parse(time);
    }

}
