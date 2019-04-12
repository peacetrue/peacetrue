package com.github.peacetrue.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.select.aggregate.AbstractAggregate;
import org.mybatis.dynamic.sql.select.aggregate.Count;

/**
 * {@link Count} 不支持 count(distinct 列名)，所以创建了{@link DistinctCount}
 *
 * @author xiayx
 */
public class DistinctCount extends AbstractAggregate<DistinctCount> {

    private DistinctCount(BasicColumn column) {
        super(column);
    }

    @Override
    protected String render(String columnName) {
        return "count(distinct " + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected DistinctCount copy() {
        return new DistinctCount(column);
    }

    public static DistinctCount of(BasicColumn column) {
        return new DistinctCount(column);
    }
}