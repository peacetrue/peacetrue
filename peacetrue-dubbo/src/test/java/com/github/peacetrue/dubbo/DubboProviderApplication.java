package com.github.peacetrue.dubbo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

/**
 * @author xiayx
 */
@SpringBootApplication
public class DubboProviderApplication {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:/com/github/peacetrue/dubbo/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "provider");
        new SpringApplicationBuilder(DubboProviderApplication.class).web(false).run(args);
    }
}
