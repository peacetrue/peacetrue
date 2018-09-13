package com.github.peacetrue.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 将所有系统配置记录到日志中
 *
 * @author xiayx
 */
@Order
public class EnvironmentLoggerApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        logger.debug("显示环境：{}", event.getEnvironment());
        MutablePropertySources propertySources = event.getEnvironment().getPropertySources();

        logger.debug("显示所有的配置信息");
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof EnumerablePropertySource) {
                loggerEnumerable((EnumerablePropertySource) propertySource, 0);
            } else {
                logger.debug("读取不可枚举的配置：{}", propertySource);
            }
        }

        logger.debug("显示生效的配置信息");
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof EnumerablePropertySource) {
                logger.debug("读取可枚举的配置：{}", propertySource);
                this.loggerLeaf((EnumerablePropertySource) propertySource, 1);
            } else {
                logger.debug("读取不可枚举的配置：{}", propertySource);
            }
        }
    }

    protected void loggerEnumerable(EnumerablePropertySource propertySource, int level) {
        logger.debug(tabs(level) + "读取可枚举配置: {}", propertySource.getName());
        if (propertySource.getSource() instanceof Collection) {
            Collection sources = (Collection) propertySource.getSource();
            for (Object source : sources) {
                if (source instanceof EnumerablePropertySource) {
                    loggerEnumerable((EnumerablePropertySource) source, level + 1);
                } else {
                    logger.debug("未处理的PropertySource：{}", source);
                }
            }
        } else {
            loggerLeaf(propertySource, level + 1);
        }
    }

    protected void loggerLeaf(EnumerablePropertySource propertySource, int level) {
        String message = tabs(level) + "{} -> {}";
        for (String name : propertySource.getPropertyNames()) {
            logger.debug(message, name, propertySource.getProperty(name));
        }
    }

    private static String tabs(int level) {
        if (level == 0) return "";
        return IntStream.range(0, level).mapToObj(value -> "\t").collect(Collectors.joining(""));
    }
}
