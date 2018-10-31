package com.github.peacetrue.dubbo.multipleregistry.providerconsumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

/**
 * @author xiayx
 */
@SpringBootApplication
public class ProviderConsumer {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:com/github/peacetrue/dubbo/multipleregistry/providerconsumer/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "providerconsumer");
        new SpringApplicationBuilder(ProviderConsumer.class).web(false).run(args);
    }
}
