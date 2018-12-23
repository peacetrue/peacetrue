package com.github.peacetrue.sign.appender;

import com.github.peacetrue.sign.SignProperties;
import com.github.peacetrue.sign.URIApp;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * the properties for client sign
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = SignAppenderProperties.PREFIX)
public class SignAppenderProperties extends SignProperties {

    public static final String PREFIX = "peacetrue.sign";

    /** 客户端应用 */
    @NestedConfigurationProperty
    private URIApp client = new URIApp();
    /** 服务端应用 */
    private List<URIApp> server = new ArrayList<>();


    public URIApp getClient() {
        return client;
    }

    public void setClient(URIApp client) {
        this.client = client;
    }

    public List<URIApp> getServer() {
        return server;
    }

    public void setServer(List<URIApp> server) {
        this.server = server;
    }

}
