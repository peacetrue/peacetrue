package com.github.peacetrue.result;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * the properties for Result
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ResultWebProperties.PREFIX)
public class ResultWebProperties {

    public static final String PREFIX = "peacetrue.result";

    /** All returned data will be converted to {@link Result} by default, If you do not want to automatically convert, config this param,  使用{@link WithoutResultWrapper}替代 */
    @Deprecated
    private Set<Class> excludeAutoConvertWhenReturn = new HashSet<>();
    /** define the unified exception page */
    private String exceptionPage = "exception";

    public Set<Class> getExcludeAutoConvertWhenReturn() {
        return excludeAutoConvertWhenReturn;
    }

    public void setExcludeAutoConvertWhenReturn(Set<Class> excludeAutoConvertWhenReturn) {
        this.excludeAutoConvertWhenReturn = excludeAutoConvertWhenReturn;
    }

    public String getExceptionPage() {
        return exceptionPage;
    }

    public void setExceptionPage(String exceptionPage) {
        this.exceptionPage = exceptionPage;
    }
}
