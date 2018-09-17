package com.github.peacetrue.result.dubbo;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author xiayx
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResultDubboApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        //properties 配置和 xml 配置 冲突，二者只能选其一而不能合并
        if (!event.getEnvironment().containsProperty("dubbo.provider.id")) return;
        MutablePropertySources propertySources = event.getEnvironment().getPropertySources();
        Properties properties = new Properties();
        properties.put("dubbo.provider.filter", "ResultExceptionFilter,-exception");
        propertySources.addLast(new PropertiesPropertySource("result-dubbo", properties));
    }
}
