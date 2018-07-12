package com.github.peacetrue.result;

import com.github.peacetrue.printer.MessageSourceClassPrinter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * the properties for Result
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ResultBuilderProperties.PREFIX)
public class ResultBuilderProperties {

    public static final String PREFIX = "peacetrue.result";

    /** the prefix of result code */
    private String codePrefix = MessageSourceResultBuilder.DEFAULT_PREFIX;
    /** the prefix of class code */
    private String classPrefix = MessageSourceClassPrinter.DEFAULT_PREFIX;
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

    public Map<String, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }

    public void addCode(String name, String alias) {
        if (getCodes().containsKey(name)) return;
        getCodes().put(name, alias);
    }
}
