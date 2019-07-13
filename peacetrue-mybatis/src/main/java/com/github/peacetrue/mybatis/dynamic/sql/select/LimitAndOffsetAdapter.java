package com.github.peacetrue.mybatis.dynamic.sql.select;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * This adapter modifies the generated SQL by adding a LIMIT and OFFSET clause at the end
 * of the generated SQL.  This can be used to create a paginated query.
 * <p>
 * LIMIT and OFFSET has limited support in relational databases, so this cannot be considered
 * a general solution for all paginated queries (and that is why this adapter lives only in the
 * test source tree and is not packaged with the core library code).
 * <p>
 * I believe it works in MySQL, HSQLDB, and Postgres.
 *
 * @author Jeff Butler
 */
public class LimitAndOffsetAdapter<R> {

    private SelectModel selectModel;
    private Function<SelectStatementProvider, R> mapperMethod;
    private int limit;
    private int offset;

    private LimitAndOffsetAdapter(SelectModel selectModel, Function<SelectStatementProvider, R> mapperMethod,
                                  int limit, int offset) {
        this.selectModel = Objects.requireNonNull(selectModel);
        this.mapperMethod = Objects.requireNonNull(mapperMethod);
        this.limit = limit;
        this.offset = offset;
    }

    public R execute() {
        return mapperMethod.apply(selectStatement());
    }

    private SelectStatementProvider selectStatement() {
        return new LimitAndOffsetDecorator(
                selectModel.render(RenderingStrategy.MYBATIS3));
    }

    public static <R> LimitAndOffsetAdapter<R> of(SelectModel selectModel,
                                                  Function<SelectStatementProvider, R> mapperMethod,
                                                  int limit, int offset) {
        return new LimitAndOffsetAdapter<>(selectModel, mapperMethod, limit, offset);
    }

    public static <R> LimitAndOffsetAdapter<R> fromZero(SelectModel selectModel, Function<SelectStatementProvider, R> mapperMethod, int limit) {
        return of(selectModel, mapperMethod, limit, 0);
    }

    public static <R> LimitAndOffsetAdapter<R> firstOne(SelectModel selectModel, Function<SelectStatementProvider, R> mapperMethod) {
        return fromZero(selectModel, mapperMethod, 1);
    }

    public class LimitAndOffsetDecorator implements SelectStatementProvider {
        private Map<String, Object> parameters = new HashMap<>();
        private String selectStatement;

        public LimitAndOffsetDecorator(SelectStatementProvider delegate) {
            parameters.putAll(delegate.getParameters());
            parameters.put("limit", limit);
            parameters.put("offset", offset);
            selectStatement = delegate.getSelectStatement() + " LIMIT #{parameters.limit} OFFSET #{parameters.offset}";
        }

        @Override
        public Map<String, Object> getParameters() {
            return parameters;
        }

        @Override
        public String getSelectStatement() {
            return selectStatement;
        }
    }
}
