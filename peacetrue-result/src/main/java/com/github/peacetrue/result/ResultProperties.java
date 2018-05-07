package com.github.peacetrue.result;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * the properties for Result
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ResultProperties.PREFIX)
public class ResultProperties {

    public static final String PREFIX = "peacetrue.result";

    /** the prefix of result code */
    private String codePrefix = "Result";
    /** the prefix of class code */
    private String classPrefix = "Class";
    /** define the unified error page */
    private String errorPage = "/error";
    /** define all standard code to custom code */
    private Map<String, String> codes = new HashMap<>();


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

}
