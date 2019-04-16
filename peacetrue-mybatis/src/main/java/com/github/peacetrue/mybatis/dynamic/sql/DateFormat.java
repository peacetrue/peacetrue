package com.github.peacetrue.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

import java.sql.JDBCType;
import java.util.Objects;
import java.util.Optional;

/**
 * DATE_FORMAT(date，format)
 *
 * @author xiayx
 */
public class DateFormat implements BindableColumn<String> {

    /** 常用的格式 */
    public static final String
            PATTERN_DATE = "%Y-%m-%d",
            PATTERN_DATE_TIME = "%Y-%m-%d %H:%i:%s",
            PATTERN_TIME = "%H:%i:%s",
            PLACEHOLDER = "";

    private BindableColumn column;
    private String pattern;
    private String alias;

    public DateFormat(BindableColumn column, String pattern) {
        this.column = Objects.requireNonNull(column);
        this.pattern = Objects.requireNonNull(pattern);
    }

    public static DateFormat of(BindableColumn column, String pattern) {
        return new DateFormat(column, pattern);
    }

    @Override
    public BindableColumn<String> as(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public Optional<JDBCType> jdbcType() {
        return Optional.of(JDBCType.VARCHAR);
    }

    @Override
    public Optional<String> typeHandler() {
        return Optional.empty();
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "DATE_FORMAT(" //$NON-NLS-1$
                + column.renderWithTableAlias(tableAliasCalculator)
                + ",'"
                + pattern
                + "')"; //$NON-NLS-1$
    }
}
