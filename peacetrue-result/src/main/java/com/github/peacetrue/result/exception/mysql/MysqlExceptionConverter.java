package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.exception.converters.ArgumentAsDataExceptionConverter;
import com.github.peacetrue.result.exception.converters.ExceptionConverter;
import com.github.peacetrue.util.RegexUtils;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

/**
 * {@link ExceptionConverter} for {@link SQLException}
 *
 * @author xiayx
 */
public class MysqlExceptionConverter extends ArgumentAsDataExceptionConverter<SQLException> {

    public static final String DEFAULT_CODE_PREFIX = "mysql_";

    private String codePrefix = DEFAULT_CODE_PREFIX;
    private Map<Integer, String> templates;

    @Override
    protected String getCode(SQLException exception) {
        return Objects.toString(codePrefix, "") + exception.getErrorCode();
    }

    @Nullable
    @Override
    protected Object[] resolveData(SQLException exception) {
        return RegexUtils.extractValue(templates.get(exception.getErrorCode()), exception.getMessage());
    }

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
