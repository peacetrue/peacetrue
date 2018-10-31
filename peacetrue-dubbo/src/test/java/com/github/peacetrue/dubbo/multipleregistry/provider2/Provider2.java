package com.github.peacetrue.dubbo.multipleregistry.provider2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

/**
 * @author xiayx
 */
@SpringBootApplication
public class Provider2 {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:com/github/peacetrue/dubbo/multipleregistry/provider2/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "provider2");
        new SpringApplicationBuilder(Provider2.class).web(false).run(args);
    }
}
