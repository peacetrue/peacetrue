package com.github.peacetrue.sign.decode;

import com.github.peacetrue.sign.AppBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

import static com.github.peacetrue.sign.decode.SignDecodeProperties.PREFIX;

/**
 * the properties for server sing
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = PREFIX)
public class SignDecodeProperties {

    public static final String PREFIX = "peacetrue.sign";

    /** the property of sign */
    private String signProperty = "sign";
    /** the apps: appId -> appSecret */
    private List<AppBean> server = new ArrayList<>();

    public String getSignProperty() {
        return signProperty;
    }

    public void setSignProperty(String signProperty) {
        this.signProperty = signProperty;
    }

    public List<AppBean> getServer() {
        return server;
    }

    public void setServer(List<AppBean> server) {
        this.server = server;
    }
}
