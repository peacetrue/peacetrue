package com.github.peacetrue.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

import java.sql.JDBCType;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CONCAT(列名...)
 */
public class Concat implements BindableColumn<String> {

    private Object[] columns;
    private String alias;

    private Concat(Object... columns) {
        this.columns = Objects.requireNonNull(columns);
    }

    public static Concat of(Object... columns) {
        Arrays.stream(columns).forEach(column -> {
            if (column instanceof BasicColumn || column instanceof String) return;
            throwException(column);
        });
        return new Concat(columns);
    }

    private static void throwException(Object column) throws IllegalArgumentException {
        String actual = Optional.ofNullable(column).map(value -> value.getClass().getName()).orElse(null);
        throw new IllegalArgumentException(String.format("the type of column must be BasicColumn or String, but found %s", actual));
    }

    @Override
    public Optional<String> alias() {
        return Optional.of(alias);
    }

    @Override
    public BindableColumn<String> as(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return Arrays.stream(columns)
                .map(item -> {
                    if (item instanceof BasicColumn) {
                        return ((BasicColumn) item).renderWithTableAlias(tableAliasCalculator);
                    } else if (item instanceof String) {
                        return "'" + item + "'";
                    } else {
                        throwException(item);
                        return null;
                    }
                })
                .collect(Collectors.joining(",", "concat(", ")"));
    }

    @Override
    public Optional<JDBCType> jdbcType() {
        return Optional.of(JDBCType.VARCHAR);
    }

    @Override
    public Optional<String> typeHandler() {
        return Optional.empty();
    }
}
