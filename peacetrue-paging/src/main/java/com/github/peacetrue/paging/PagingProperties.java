package com.github.peacetrue.paging;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * the properties for paging
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = "peacetrue.paging")
public class PagingProperties {

    private Map<PageAttribute, String> source;
    private Map<PageAttribute, String> target;

    public Map<PageAttribute, String> getSource() {
        return source;
    }

    public void setSource(Map<PageAttribute, String> source) {
        this.source = source;
    }

    public Map<PageAttribute, String> getTarget() {
        return target;
    }

    public void setTarget(Map<PageAttribute, String> target) {
        this.target = target;
    }
}
