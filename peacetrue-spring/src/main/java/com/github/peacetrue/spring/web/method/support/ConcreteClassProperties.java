package com.github.peacetrue.spring.web.method.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.spring")
public class ConcreteClassProperties {
    private Map<String, String> concreteClasses = new HashMap<>();
}
