package com.github.peacetrue.export;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@ConfigurationProperties
public class ExportProperties {

    /** 单元格内容前缀 */
    private String prefix = ",";
    /** 默认的单元格内容 */
    private String defaultContent = "--";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }
}
