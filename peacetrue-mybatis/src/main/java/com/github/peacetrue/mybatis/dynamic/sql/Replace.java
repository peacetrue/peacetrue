package com.github.peacetrue.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

import java.sql.JDBCType;
import java.util.Objects;
import java.util.Optional;

/**
 * REPLACE ( string_expression , string_pattern , string_replacement )
 *
 * @author xiayx
 */
public class Replace implements BindableColumn<String> {

    private BindableColumn column;
    private String oldValue;
    private String newValue;

    public Replace(BindableColumn column, String oldValue, String newValue) {
        this.column = Objects.requireNonNull(column);
        this.oldValue = Objects.requireNonNull(oldValue);
        this.newValue = Objects.requireNonNull(newValue);
    }

    public static Replace of(BindableColumn column, String oldValue, String newValue) {
        return new Replace(column, oldValue, newValue);
    }

    @Override
    public BindableColumn<String> as(String alias) {
        this.newValue = alias;
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
        return Optional.ofNullable(newValue);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "REPLACE(" //$NON-NLS-1$
                + column.renderWithTableAlias(tableAliasCalculator)
                + ",'"
                + oldValue
                + "','"
                + newValue
                + "')"; //$NON-NLS-1$
    }
}
