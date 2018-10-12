package com.github.peacetrue.dubbo;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author xiayx
 */
@Order
public class DubboContextApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        //properties 配置和 xml 配置 冲突，二者只能选其一而不能合并
        ConfigurableEnvironment environment = event.getEnvironment();
        //如果未使用属性配置，略过
        if (!environment.containsProperty("dubbo.application.id")) return;
        //默认情况下自动启用【自定义上下文】，可以手动关闭
        if (!environment.getProperty("dubbo.customContext", Boolean.class)) return;
        //添加过滤器，实现【自定义上下文】
        PropertiesPropertySource propertySource = (PropertiesPropertySource)environment.getPropertySources().get("dubbo");
        if (propertySource == null) {
            propertySource = new PropertiesPropertySource("dubbo", new Properties());
            environment.getPropertySources().addFirst(propertySource);
        }
        String previousFilter = environment.getProperty("dubbo.consumer.filter");
        previousFilter = previousFilter == null ? "" : "," + previousFilter;
        propertySource.getSource().put("dubbo.consumer.filter", DubboContextFilter.FILTER_NAME + previousFilter);
    }
}
