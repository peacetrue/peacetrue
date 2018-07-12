package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.exception.ExceptionConverter;
import com.github.peacetrue.result.exception.SimplifiedExceptionConverter;
import com.github.peacetrue.util.RegexUtils;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Map;

/**
 * {@link ExceptionConverter} for {@link SQLException}
 *
 * @author xiayx
 */
public class MysqlExceptionConverter extends SimplifiedExceptionConverter<SQLException> {

    private Map<String, String> sqlStateMessagePatterns;

    @Nullable
    @Override
    protected Object[] resolveData(SQLException exception) {
        String messagePattern = sqlStateMessagePatterns.get(exception.getSQLState());
        if (messagePattern == null) return null;
        return RegexUtils.extractValue(messagePattern, exception.getMessage());
    }

    public Map<String, String> getSqlStateMessagePatterns() {
        return sqlStateMessagePatterns;
    }

    public void setSqlStateMessagePatterns(Map<String, String> sqlStateMessagePatterns) {
        this.sqlStateMessagePatterns = sqlStateMessagePatterns;
    }
}
