package com.github.peacetrue.enums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiayx
 */
@SpringBootApplication
public class EnumApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(EnumApplication.class, args);
//        System.out.println(new ObjectMapper().writeValueAsString(Applications.values()));
    }

}
