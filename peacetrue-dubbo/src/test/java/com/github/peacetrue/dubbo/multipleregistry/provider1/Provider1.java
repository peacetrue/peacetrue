package com.github.peacetrue.dubbo.multipleregistry.provider1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

/**
 * @author xiayx
 */
@SpringBootApplication
public class Provider1 {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:com/github/peacetrue/dubbo/multipleregistry/provider1/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "provider1");
        new SpringApplicationBuilder(Provider1.class).web(false).run(args);
    }
}
