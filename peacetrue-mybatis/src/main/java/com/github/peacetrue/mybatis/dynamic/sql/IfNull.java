package com.github.peacetrue.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

import java.util.Optional;

/**
 * IFNULL(列名,默认值)
 */
public class IfNull implements BasicColumn {

    private BasicColumn column;
    private BasicColumn defaultValue;
    private String alias;

    public IfNull(BasicColumn column, BasicColumn defaultValue) {
        this.column = column;
        this.defaultValue = defaultValue;
    }

    public static IfNull of(BasicColumn column, BasicColumn defaultValue) {
        return new IfNull(column, defaultValue);
    }

    @Override
    public Optional<String> alias() {
        return Optional.of(alias);
    }

    @Override
    public BasicColumn as(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "IFNULL(" +
                column.renderWithTableAlias(tableAliasCalculator)
                + "," +
                defaultValue.renderWithTableAlias(tableAliasCalculator)
                + ")";
    }

}
