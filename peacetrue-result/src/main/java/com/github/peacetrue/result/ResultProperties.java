package com.github.peacetrue.result;

import com.github.peacetrue.printer.MessageSourceClassPrinter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * the properties for Result
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ResultProperties.PREFIX)
public class ResultProperties {

    public static final String PREFIX = "peacetrue.result";

    /** the prefix of result code */
    private String codePrefix = MessageSourceResultBuilder.DEFAULT_PREFIX;
    /** the prefix of class code */
    private String classPrefix = MessageSourceClassPrinter.DEFAULT_PREFIX;
    /** define the unified error page */
    private String errorPage = "error";
    /** define all standard code to custom code */
    private Map<String, String> codes = new HashMap<>();
    /** All returned data will be converted to {@link Result} by default, If you do not want to automatically convert, config this param */
    private Set<Class> excludeAutoConvertWhenReturn = new HashSet<>();

    public String getCodePrefix() {
        return codePrefix;
    }

    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public String getClassPrefix() {
        return classPrefix;
    }

    public void setClassPrefix(String classPrefix) {
        this.classPrefix = classPrefix;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public Map<String, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }

    public Set<Class> getExcludeAutoConvertWhenReturn() {
        return excludeAutoConvertWhenReturn;
    }

    public void setExcludeAutoConvertWhenReturn(Set<Class> excludeAutoConvertWhenReturn) {
        this.excludeAutoConvertWhenReturn = excludeAutoConvertWhenReturn;
    }
}
