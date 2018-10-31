package com.github.peacetrue.sign.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

/**
 * the application for server sign
 *
 * @author xiayx
 */
@SpringBootApplication
public class ClientSignApplication {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "client");
        SpringApplication.run(ClientSignApplication.class, args);
    }
}
