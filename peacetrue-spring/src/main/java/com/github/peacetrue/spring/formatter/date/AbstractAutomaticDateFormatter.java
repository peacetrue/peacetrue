package com.github.peacetrue.spring.formatter.date;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
public abstract class AbstractAutomaticDateFormatter<T extends Date> implements Formatter<T> {

    /** 解析器 */
    private Map<Integer, String> parsers;
    /** 格式器 */
    private DateFormat format;

    public AbstractAutomaticDateFormatter(String... patterns) {
        this(patterns, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public AbstractAutomaticDateFormatter(String[] patterns, DateFormat format) {
        this.format = Objects.requireNonNull(format);
        parsers = new HashMap<>(patterns.length);
        for (String pattern : patterns) {
            parsers.put(pattern.length(), pattern);
        }
    }


    public T parse(String text, Locale locale) throws ParseException {
        if (!StringUtils.hasText(text)) return null;

        text = text.trim();

        if (text.chars().allMatch(Character::isDigit)) {
            return parseLong(Long.parseLong(text));
        }

        String pattern = parsers.get(text.length());
        if (pattern == null) {
            throw new IllegalArgumentException("未找到与'" + text + "'匹配的日期格式化器");
        }

        return parseString(new SimpleDateFormat(pattern), text);
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
    protected abstract T parseString(DateFormat parse, String time) throws ParseException;

    @Override
    public String print(T object, Locale locale) {
        return format.format(object);
    }
}
