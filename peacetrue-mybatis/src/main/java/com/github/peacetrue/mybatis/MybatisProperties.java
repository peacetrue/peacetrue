package com.github.peacetrue.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.peacetrue.mybatis.spring.RowBoundsHandlerMethodArgumentResolver.*;

/**
 * properties for mybatis
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = "peacetrue.mybatis")
public class MybatisProperties {

    private String offsetParameterName = DEFAULT_OFFSET_PARAMETER_NAME;
    private String limitParameterName = DEFAULT_LIMIT_PARAMETER_NAME;
    private Integer defaultOffsetParameterValue = DEFAULT_OFFSET_PARAMETER_VALUE;
    private Integer defaultSizeParameterValue = DEFAULT_LIMIT_PARAMETER_VALUE;

    public String getOffsetParameterName() {
        return offsetParameterName;
    }

    public void setOffsetParameterName(String offsetParameterName) {
        this.offsetParameterName = offsetParameterName;
    }

    public String getLimitParameterName() {
        return limitParameterName;
    }

    public void setLimitParameterName(String limitParameterName) {
        this.limitParameterName = limitParameterName;
    }

    public Integer getDefaultOffsetParameterValue() {
        return defaultOffsetParameterValue;
    }

    public void setDefaultOffsetParameterValue(Integer defaultOffsetParameterValue) {
        this.defaultOffsetParameterValue = defaultOffsetParameterValue;
    }

    public Integer getDefaultSizeParameterValue() {
        return defaultSizeParameterValue;
    }

    public void setDefaultSizeParameterValue(Integer defaultSizeParameterValue) {
        this.defaultSizeParameterValue = defaultSizeParameterValue;
    }
}
