package com.github.peacetrue.sign.append;

import com.github.peacetrue.sign.SignProperties;
import com.github.peacetrue.sign.UriApp;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * the properties for client sign
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = SignAppendProperties.PREFIX)
public class SignAppendProperties extends SignProperties {

    public static final String PREFIX = "peacetrue.sign";

    /** 客户端应用 */
    private UriApp client = new UriApp();
    /** 服务端应用 */
    private List<UriApp> server = new ArrayList<>();


    public UriApp getClient() {
        return client;
    }

    public void setClient(UriApp client) {
        this.client = client;
    }

    public List<UriApp> getServer() {
        return server;
    }

    public void setServer(List<UriApp> server) {
        this.server = server;
    }

}
