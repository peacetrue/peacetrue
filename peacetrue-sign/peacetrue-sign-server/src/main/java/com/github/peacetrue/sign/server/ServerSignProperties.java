package com.github.peacetrue.sign.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

import static com.github.peacetrue.sign.server.ServerSignProperties.PREFIX;

/**
 * the properties for server sing
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = PREFIX)
public class ServerSignProperties {

    public static final String PREFIX = "peacetrue.sign.server";

    /** the property of sign */
    private String signProperty = "sign";
    /** the consumer apps */
    private Map<String, String> apps = new HashMap<>();


    public String getSignProperty() {
        return signProperty;
    }

    public void setSignProperty(String signProperty) {
        this.signProperty = signProperty;
    }

    public Map<String, String> getApps() {
        return apps;
    }

    public void setApps(Map<String, String> apps) {
        this.apps = apps;
    }
}
