package com.github.peacetrue.spring.formatter.date;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Objects;

/**
 * 自动的日期转换器
 * <p>
 * <ul>
 * <li>如果输入为数值，根据毫秒数转换</li>
 * <li>如果输入不为数值，根据输入值的长度查找对应的日期规则，进行转换</li>
 * </ul>
 *
 * @author xiayx
 */
public abstract class AbstractAutomaticLocalDateFormatter<T extends TemporalAccessor> implements Formatter<T> {

    /** 解析器 */
    private DateTimeFormatter parser;
    /** 格式器 */
    private DateTimeFormatter formatter;

    public AbstractAutomaticLocalDateFormatter(DateTimeFormatter parser) {
        this(parser, parser);
    }

    public AbstractAutomaticLocalDateFormatter(DateTimeFormatter parser, DateTimeFormatter formatter) {
        this.parser = Objects.requireNonNull(parser);
        this.formatter = Objects.requireNonNull(formatter);
    }

    public T parse(String text, Locale locale) {
        if (!StringUtils.hasText(text)) return null;

        text = text.trim();

        if (text.chars().allMatch(Character::isDigit)) {
            return parseLong(Long.parseLong(text));
        }

        return parseString(parser, text);
    }

    /**
     * 把Long型输入转换为日期
     *
     * @param time Long型时间
     * @return 日期时间
     */
    protected abstract T parseLong(Long time);

    /**
     * 把String型输入转换为日期
     *
     * @param time String型时间
     * @return 日期时间
     */
    protected abstract T parseString(DateTimeFormatter parse, String time);

    @Override
    public String print(T object, Locale locale) {
        return formatter.format(object);
    }
}
