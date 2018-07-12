package com.github.peacetrue.spring.boot;

import com.github.peacetrue.util.AssertUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * tests for {@link BootBasePackageProvider}
 *
 * @author xiayx
 */

public class BootBasePackageProviderTest {

    public static class BasePackageConsumer {
        private String basePackage;

        public BasePackageConsumer(String basePackage) {
            this.basePackage = basePackage;
        }
    }

    @Configuration
    public static class BaseConfiguration {
        @Bean
        public BasePackageProvider basePackageProvider() {
            return new BootBasePackageProvider();
        }

        @Bean
        public BasePackageConsumer basePackageConsumer() {
            return new BasePackageConsumer(basePackageProvider().getBasePackage());
        }
    }

    @SpringBootApplication
    public static class SpringBootApplicationClass extends BaseConfiguration {
        @Bean
        public String foo() {
            return "";
        }
    }

    @SpringBootConfiguration
    public static class SpringBootConfigurationClass extends BaseConfiguration {
        @Bean
        public String foo() {
            return "";
        }
    }


    public void withAnnotation(Class<?> clazz) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(clazz).web(false).build().run();
        BasePackageProvider basePackageProvider = context.getBean(BasePackageProvider.class);
        String name = BootBasePackageProviderTest.class.getPackage().getName();
        Assert.assertEquals(name, basePackageProvider.getBasePackage());
        BasePackageConsumer basePackageConsumer = context.getBean(BasePackageConsumer.class);
        Assert.assertEquals(name, basePackageConsumer.basePackage);
    }

    @Test
    public void springBootApplication() throws Exception {
        withAnnotation(SpringBootApplicationClass.class);
    }

    @Test
    public void springBootConfiguration() throws Exception {
        withAnnotation(SpringBootConfigurationClass.class);
    }

    @Test
    public void exception() throws Exception {
        AssertUtils.assertException(() -> withAnnotation(BaseConfiguration.class));
    }


}