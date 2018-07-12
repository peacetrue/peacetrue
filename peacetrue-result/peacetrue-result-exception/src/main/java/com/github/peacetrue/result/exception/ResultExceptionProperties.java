package com.github.peacetrue.result.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * the properties for Result Exception
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = ResultExceptionProperties.PREFIX)
public class ResultExceptionProperties {

    private static Logger logger = LoggerFactory.getLogger(ResultExceptionProperties.class);

    public static final String PREFIX = "peacetrue.result.exception";
    /** the proxy exception, delegate to {@link Exception#getCause()} as implement */
    private List<Class<? extends Exception>> proxyClasses = new ArrayList<>();


    public List<Class<? extends Exception>> getProxyClasses() {
        return proxyClasses;
    }

    public void setProxyClasses(List<Class<? extends Exception>> proxyClasses) {
        this.proxyClasses = proxyClasses;
    }

    @SuppressWarnings("unchecked")
    public void addProxyClasses(String... proxyClasses) {
        this.proxyClasses.addAll(resolveClasses(proxyClasses, Exception.class));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> resolveClasses(String[] stringClasses, Class<T> targetClass) {
        Set<Class<? extends T>> classes = new HashSet<>(stringClasses.length);
        for (String stringClass : stringClasses) {
            try {
                Class<?> aClass = Class.forName(stringClass);
                if (targetClass == null || targetClass.isAssignableFrom(aClass)) {
                    classes.add((Class<? extends T>) aClass);
                } else {
                    logger.warn("ignored class '{}' which is not subclass of '{}'", stringClass, targetClass);
                }
            } catch (ClassNotFoundException ignored) {
                logger.warn("ignored class '{}' which is unknown", stringClass);
            }
        }
        return classes;
    }
}
