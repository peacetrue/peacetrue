package com.github.peacetrue.log;

import com.github.peacetrue.log.service.AbstractLog;
import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
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

    /** 是否启用日志拦截，默认启用。如果只想使用基础服务类，可关闭日志拦截， */
    private Boolean enabled;
    /** 是否异步拦截，默认启用。异步执行会导致{@link ThreadLocal}内数据丢失 */
    private Boolean async;
    /** 具体的日志类 */
    private Class<? extends AbstractLog> concreteClass;

}