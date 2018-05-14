package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.exception.ResultExceptionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

import static com.github.peacetrue.result.exception.mysql.MysqlExceptionConverter.DEFAULT_CODE_PREFIX;

/**
 * the properties for mysql exception convert
 *
 * @author xiayx
 * @see com.mysql.jdbc.MysqlErrorNumbers
 */
@ConfigurationProperties(prefix = ResultMysqlExceptionProperties.PREFIX)
public class ResultMysqlExceptionProperties {

    public static final String PREFIX = ResultExceptionProperties.PREFIX + ".mysql";

    private String codePrefix = DEFAULT_CODE_PREFIX;
    private Map<Integer, String> templates = new HashMap<>();

    public String getCodePrefix() {
        return codePrefix;
    }

    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public Map<Integer, String> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<Integer, String> templates) {
        this.templates = templates;
    }
}
