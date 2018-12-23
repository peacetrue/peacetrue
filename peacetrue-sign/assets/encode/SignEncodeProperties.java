package com.github.peacetrue.sign.encode;

import com.github.peacetrue.sign.URIApp;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * the properties for client sign
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = SignEncodeProperties.PREFIX)
public class SignEncodeProperties {

    public static final String PREFIX = "peacetrue.sign";

    private URIApp client = new URIApp();
    /** Configure the served application */
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
