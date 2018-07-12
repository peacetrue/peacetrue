package com.github.peacetrue.sign.client;

import com.github.peacetrue.sign.AppBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * the properties for client sign
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ClientSignProperties.PREFIX)
public class ClientSignProperties {

    public static final String PREFIX = "peacetrue.sign.client";

    /** Configure the application for each service */
    private Map<String, AppBean> serviceApps = new LinkedHashMap<>();
    /** Configure the served application */
    private List<AppBean> consumerApps = new ArrayList<>();

    public Map<String, AppBean> getServiceApps() {
        return serviceApps;
    }

    public void setServiceApps(Map<String, AppBean> serviceApps) {
        this.serviceApps = serviceApps;
    }

    public List<AppBean> getConsumerApps() {
        return consumerApps;
    }

    public void setConsumerApps(List<AppBean> consumerApps) {
        this.consumerApps = consumerApps;
    }
}
