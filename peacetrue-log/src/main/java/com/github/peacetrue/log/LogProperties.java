package com.github.peacetrue.log;

import com.github.peacetrue.log.service.AbstractLog;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置
 *
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = LogProperties.PREFIX)
public class LogProperties {

    public static final String PREFIX = "peacetrue.log";

    /** 是否启用日志拦截，默认启用 */
    private boolean enabled;
    /** 具体的日志类 */
    private Class<? extends AbstractLog> concreteClass;

}