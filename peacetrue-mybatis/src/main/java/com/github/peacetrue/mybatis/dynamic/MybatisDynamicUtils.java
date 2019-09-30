package com.github.peacetrue.mybatis.dynamic;

import com.github.peacetrue.util.DateUtils;
import com.google.common.base.CaseFormat;
import org.mybatis.dynamic.sql.*;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.springframework.data.domain.Sort;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

/**
 * Mybatis工具类。针对于 mybatis-dynamic-sql 的辅助处理
 *
 * @author xiayx
 */
public abstract class MybatisDynamicUtils {

    private static final Map<SqlTable, SqlColumn[]> SQL_COLUMNS = new HashMap<>();

    /** 设置表名 */
    public static void setTableName(SqlTable sqlTable, String tableName) {
        Field field = ReflectionUtils.findField(sqlTable.getClass(), "nameSupplier", Supplier.class);
        field.setAccessible(true);
        ReflectionUtils.setField(field, sqlTable, (Supplier) () -> tableName);
    }


    /*-----------SELECT----------*/

    /** 获取表的所有列，附加缓存 */
    public static SqlColumn[] getSqlColumns(SqlTable sqlTable) {
        return SQL_COLUMNS.computeIfAbsent(sqlTable, MybatisDynamicUtils::getSqlColumnsInternal);
    }

    /** 获取表的所有列，便于查询所有列时使用 */
    private static SqlColumn[] getSqlColumnsInternal(SqlTable sqlTable) {
        return Arrays.stream(sqlTable.getClass().getDeclaredFields())
                .map(field -> ReflectionUtils.getField(field, sqlTable)).toArray(SqlColumn[]::new);
    }

    /** 构造SELECT列，用于连表查询时构造组合列 */
    public static SqlColumnsBuilder sqlColumns(SqlTable sqlTable) {
        return sqlColumns(getSqlColumns(sqlTable));
    }

    /** 构造SELECT列，用于连表查询时构造组合列 */
    public static SqlColumnsBuilder sqlColumns(BasicColumn... sqlColumns) {
        return new SqlColumnsBuilder().append(sqlColumns);
    }

    /*-----------WHERE----------*/

    /** 空字符串转换成{@code null}，便于对接{@link SqlBuilder}的判断性条件，例如{@link SqlBuilder#isEqualToWhenPresent(Object)} */
    public static String trimToNull(String string) {
        if (string == null) return null;
        String trim = string.trim();
        return trim.length() == 0 ? null : trim;
    }

    /** like值，添加like匹配词汇 */
    public static String likeValue(String string) {
        string = trimToNull(string);
        return string == null ? null : "%" + string + "%";
    }

    /** 结束日期值，前端传入的值精确至日期，后端筛选需要使用当日最大值 */
    public static Date endDateValue(Date date) {
        return date == null ? null : DateUtils.fromLocalDateTime(DateUtils.toLocalDateTime(date).truncatedTo(ChronoUnit.DAYS).plusDays(1));
    }

    /** 结束日期值，前端传入的值精确至日期，后端筛选需要使用当日最大值 */
    public static LocalDateTime endDateValue(LocalDateTime date) {
        return date == null ? null : date.truncatedTo(ChronoUnit.DAYS);
    }

    public static final VisitableCondition PLACEHOLDER_CONDITION = new VisitableCondition() {
        public Object accept(ConditionVisitor visitor) {
            return null;
        }

        public boolean shouldRender() {
            return false;
        }
    };

    /** 占位条件，始终不会匹配上 */
    @SuppressWarnings("unchecked")
    public static <T> VisitableCondition<T> getPlaceholderCondition() {
        return PLACEHOLDER_CONDITION;
    }

    /** 获取条件当有值时 */
    public static <T, V> VisitableCondition<T> getConditionWhenPresent(V value, Function<V, VisitableCondition<T>> function) {
        return value == null ? getPlaceholderCondition() : function.apply(value);
    }

    /*-----------ORDER BY----------*/
    public static BasicColumn[] findSqlColumns(SqlTable sqlTable, String... names) {
        SqlColumn[] sqlColumns = getSqlColumns(sqlTable);
        return Arrays.stream(names).map(name -> Arrays.stream(sqlColumns).anyMatch(s -> name.equals(s.name()))).toArray(SqlColumn[]::new);
    }

    /** 获取排序属性，将{@link Sort}转换为{@link QueryExpressionDSL#orderBy(SortSpecification...)}的入参 */
    public static SortSpecification[] orders(SqlTable sqlTable, Sort sort) {
        if (sort == null) return new SortSpecification[]{findSqlColumn(sqlTable, "id")};
        return StreamSupport.stream(sort.spliterator(), false)
                .map(order -> order(sqlTable, order))
                .toArray(SortSpecification[]::new);
    }

    /** 获取排序属性（单个属性） */
    private static SortSpecification order(SqlTable sqlTable, Sort.Order order) {
        SqlColumn sqlColumn = findSqlColumn(sqlTable, propertyNameToColumnName(order.getProperty()));
        return order.getDirection() == Sort.Direction.ASC ? sqlColumn : sqlColumn.descending();
    }

    /** 根据列名查找{@link SqlColumn} */
    private static SqlColumn findSqlColumn(SqlTable sqlTable, String columnName) {
        return Arrays.stream(getSqlColumns(sqlTable))
                .filter(sqlColumn -> sqlColumn.name().equals(columnName)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("the column name '%s' can't be found in '%s'", columnName, sqlTable)
                ));
    }

    /** 属性名转列名 */
    public static String propertyNameToColumnName(String propertyName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, propertyName);
    }

    public static void main(String[] args) {
        System.out.println(propertyNameToColumnName("someOne"));
    }

    public static class SqlColumnsBuilder {

        private List<BasicColumn> sqlColumns = new ArrayList<>();
        private String associate;

        private String getAlias(SqlColumn sqlColumn) {
            return associate + "_" + sqlColumn.name();
        }

        public SqlColumnsBuilder join(String associate) {
            this.associate = associate;
            return this;
        }

        public SqlColumnsBuilder append(SqlColumn sqlColumn) {
            this.sqlColumns.add(associate == null ? sqlColumn : sqlColumn.as(getAlias(sqlColumn)));
            return this;
        }

        public SqlColumnsBuilder append(SqlColumn... sqlColumns) {
            Arrays.stream(sqlColumns).forEach(this::append);
            return this;
        }

        public SqlColumnsBuilder append(BasicColumn basicColumn) {
            this.sqlColumns.add(basicColumn);
            return this;
        }

        public SqlColumnsBuilder append(BasicColumn... basicColumn) {
            Arrays.stream(basicColumn).forEach(this::append);
            return this;
        }

        public BasicColumn[] build() {
            return sqlColumns.toArray(new BasicColumn[sqlColumns.size()]);
        }
    }


}
