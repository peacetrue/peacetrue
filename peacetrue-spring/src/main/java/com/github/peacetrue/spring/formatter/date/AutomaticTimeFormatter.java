package com.github.peacetrue.spring.formatter.date;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * 自动的时间转换器
 *
 * @author xiayx
 */
public class AutomaticTimeFormatter extends AbstractAutomaticDateFormatter<Time> {

    public AutomaticTimeFormatter() {
        super("HH:mm", "HH:mm:ss");
    }

    public AutomaticTimeFormatter(String[] patterns, DateFormat format) {
        super(patterns, format);
    }

    @Override
    protected Time parseLong(Long time) {
        return new Time(time);
    }

    @Override
    protected Time parseString(DateFormat parse, String time) throws ParseException {
        return new Time(parse.parse(time).getTime());
    }

}
