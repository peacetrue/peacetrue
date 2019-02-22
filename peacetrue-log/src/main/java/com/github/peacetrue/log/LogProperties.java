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

    /** 是否启用日志拦截 */
    private boolean enabled;

    /** the subclass of {@link AbstractLog} */
    private Class<? extends AbstractLog> concreteClass;

}