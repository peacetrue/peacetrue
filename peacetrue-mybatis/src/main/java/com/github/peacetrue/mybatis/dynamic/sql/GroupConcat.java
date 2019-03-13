package com.github.peacetrue.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

import javax.annotation.Nullable;
import java.sql.JDBCType;
import java.util.Objects;
import java.util.Optional;

/**
 * group_concat( [DISTINCT]  要连接的字段   [Order BY 排序字段 ASC/DESC]   [Separator '分隔符'] )
 */
public class GroupConcat implements BindableColumn<String> {

    private BasicColumn concatColumn;
    private SortSpecification sortColumn;
    private String separator;
    private String alias;

    private GroupConcat(SqlColumn concatColumn) {
        this.concatColumn = Objects.requireNonNull(concatColumn);
    }

    public static GroupConcat of(SqlColumn concatColumn) {
        return new GroupConcat(concatColumn);
    }

    public GroupConcat withSort(SortSpecification sortColumn) {
        this.sortColumn = sortColumn;
        return this;
    }

    public GroupConcat withSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    public GroupConcat withAlias(String alias) {
        return as(alias);
    }

    @Nullable
    public String getFirstValue(@Nullable String value) {
        if (value == null) return null;
        return value.split(separator == null ? "," : separator)[0];
    }

    @Override
    public GroupConcat as(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        StringBuilder builder = new StringBuilder();
        builder.append("GROUP_CONCAT(");
        builder.append(concatColumn.renderWithTableAlias(tableAliasCalculator));
        if (sortColumn != null) {
            builder.append(" ORDER BY ");
            if (sortColumn instanceof SqlColumn) {
                builder.append(((SqlColumn) sortColumn).renderWithTableAlias(tableAliasCalculator));
            } else {
                builder.append(sortColumn.aliasOrName());
            }
            if (sortColumn.isDescending()) builder.append(" DESC");
        }
        if (separator != null) {
            builder.append(" SEPARATOR '").append(separator).append("'");
        }

        builder.append(")");
        return builder.toString();
    }

    @Override
    public Optional<JDBCType> jdbcType() {
        return Optional.of(JDBCType.VARCHAR);
    }

    @Override
    public Optional<String> typeHandler() {
        //TODO don't know how to do
        return Optional.of("string");
    }
}
