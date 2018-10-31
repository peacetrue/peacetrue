package com.github.peacetrue.sign.server;

import com.github.peacetrue.sign.append.SignAppendAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

/**
 * the application for server sign
 *
 * @author xiayx
 */
@SpringBootApplication(exclude = SignAppendAutoConfiguration.class)
public class ServerSignApplication {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "server");
        SpringApplication.run(ServerSignApplication.class, args);
    }
}
