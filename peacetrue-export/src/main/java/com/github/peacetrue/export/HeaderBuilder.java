package com.github.peacetrue.export;

import org.springframework.beans.DirectFieldAccessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author xiayx
 */
public class HeaderBuilder {

    private static final Map<Boolean, String> BOOLEAN_CONVERTER = new HashMap<>();

    static {
        BOOLEAN_CONVERTER.put(true, "是");
        BOOLEAN_CONVERTER.put(false, "否");
    }

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final Function<Object, String> FORMATTER_OBJECT = Object::toString;
    public static final Function<Boolean, String> FORMATTER_BOOLEAN = BOOLEAN_CONVERTER::get;


    private List<Cell> cells = new LinkedList<>();
    private Map<Object, DirectFieldAccessor> accessors = new HashMap<>();
    private Map<Boolean, String> booleanConverter = BOOLEAN_CONVERTER;
    private String datePattern = DATE_PATTERN;
    private String prefix = ",";

    public static HeaderBuilder builder() {return new HeaderBuilder();}

    /** 追加表头内容和列转换器 */
    public <T> HeaderBuilder append(String content, Function<T, String> formatter) {
        cells.add(new Cell<>(content, formatter));
        return this;
    }

    /** 为最后操作的单元格添加列转换器 */
    @SuppressWarnings("unchecked")
    public <T> HeaderBuilder append(Function<String, String> formatter) {
        Cell<T> cell = this.cells.get(this.cells.size() - 1);
        cell.setFormatter(cell.getFormatter().andThen(t -> t == null ? null : formatter.apply(t)));
        return this;
    }

    /** 追加表头内容和属性值转换器 */
    @SuppressWarnings("unchecked")
    public <T> HeaderBuilder append(String content, String property, Function<T, String> formatter) {
        Function<Object, T> propertyFormatter = (t) -> (T) accessors.computeIfAbsent(t, this::buildDirectFieldAccessor).getPropertyValue(property);
        append(content, propertyFormatter.andThen(t -> t == null ? null : formatter.apply(t)));
        return this;
    }

    private DirectFieldAccessor buildDirectFieldAccessor(Object o) {
        DirectFieldAccessor directFieldAccessor = new DirectFieldAccessor(o);
        directFieldAccessor.setAutoGrowNestedPaths(true);
        return directFieldAccessor;
    }

    /** 追加表头内容和属性值，直接用{@link Object#toString()}输出 */
    public HeaderBuilder appendRaw(String content, String property) {
        append(content, property, FORMATTER_OBJECT);
        return this;
    }

    /** 追加表头内容和日期类型属性值，需要使用日期格式化器转换 */
    public HeaderBuilder appendDate(String content, String property, DateFormat dateFormat) {
        append(content, property, dateFormat::format);
        return this;
    }

    /** @see #appendDate(String, String, DateFormat) */
    public HeaderBuilder appendDate(String content, String property, String pattern) {
        return appendDate(content, property, new SimpleDateFormat(pattern));
    }

    /** @see #appendDate(String, String, DateFormat) */
    public HeaderBuilder appendDate(String content, String property) {
        return appendDate(content, property, datePattern);
    }

    /** 追加表头内容和布尔类型属性值，需要使用布尔转换器 */
    public HeaderBuilder appendBoolean(String content, String property, Map<Boolean, String> booleanConverter) {
        append(content, property, booleanConverter::get);
        return this;
    }

    /** @see #appendBoolean(String, String, Map) */
    public HeaderBuilder appendBoolean(String content, String property) {
        return appendBoolean(content, property, booleanConverter);
    }

    /** 数值内容过长无法展示时，添加一个前缀，可使其展示 */
    public HeaderBuilder prependPrefix(String prefix) {
        return append(t -> prefix + t);
    }

    /** @see #prependPrefix(String) */
    public HeaderBuilder prependPrefix() {
        return prependPrefix(prefix);
    }

    public Header build() {
        return new Header() {
            public int getCount() {return cells.size();}

            public Cell getCell(int i) {return cells.get(i);}
        };
    }

    /**
     * @author xiayx
     */
    public static class Cell<T> implements Header.Cell<T> {

        private String content;
        private Function<T, String> formatter;

        public Cell(String content, Function<T, String> formatter) {
            this.content = content;
            this.formatter = formatter;
        }

        @Override
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public Function<T, String> getFormatter() {
            return formatter;
        }

        public void setFormatter(Function<T, String> formatter) {
            this.formatter = formatter;
        }
    }
}
