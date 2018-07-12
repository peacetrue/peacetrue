package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.exception.ResultExceptionProperties;
import com.mysql.jdbc.SQLError;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * the properties for mysql exception convert
 *
 * @author xiayx
 * @see com.mysql.jdbc.MysqlErrorNumbers
 */
@ConfigurationProperties(prefix = ResultExceptionProperties.PREFIX)
public class ResultMysqlExceptionProperties {

    private Map<String, String> sqlStateMessagePatterns = new HashMap<>();

    public ResultMysqlExceptionProperties() {
        sqlStateMessagePatterns.put(SQLError.SQL_STATE_INTEGRITY_CONSTRAINT_VIOLATION, "Duplicate entry '(\\w+)' for key '(\\w+)'");
    }

    public Map<String, String> getSqlStateMessagePatterns() {
        return sqlStateMessagePatterns;
    }

    public void setSqlStateMessagePatterns(Map<String, String> sqlStateMessagePatterns) {
        this.sqlStateMessagePatterns = sqlStateMessagePatterns;
    }
}
