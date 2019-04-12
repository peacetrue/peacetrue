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

    private BasicColumn[] columns;
    private String alias;

    private Concat(BasicColumn... columns) {
        this.columns = Objects.requireNonNull(columns);
    }

    public static Concat of(BasicColumn... columns) {
        return new Concat(columns);
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
                .map(item -> item.renderWithTableAlias(tableAliasCalculator))
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
