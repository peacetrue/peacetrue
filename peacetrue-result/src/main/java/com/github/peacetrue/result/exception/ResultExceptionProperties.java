package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * the properties for Result Exception
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ResultExceptionProperties.PREFIX)
public class ResultExceptionProperties {

    public static final String PREFIX = ResultProperties.PREFIX + ".exception";

    /** the proxy exception, delegate to {@link Exception#getCause()} as implement */
    private List<Class<? extends Exception>> proxyClasses = new ArrayList<>();

    public List<Class<? extends Exception>> getProxyClasses() {
        return proxyClasses;
    }

    public void setProxyClasses(List<Class<? extends Exception>> proxyClasses) {
        this.proxyClasses = proxyClasses;
    }
}
