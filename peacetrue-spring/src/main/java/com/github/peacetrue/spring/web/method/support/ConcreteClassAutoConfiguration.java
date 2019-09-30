package com.github.peacetrue.spring.web.method.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
@Configuration
@ConditionalOnClass({WebMvcConfigurer.class, ModelAttributeMethodProcessor.class})
@EnableConfigurationProperties(ConcreteClassProperties.class)
public class ConcreteClassAutoConfiguration {

    private ConcreteClassProperties properties;

    public ConcreteClassAutoConfiguration(ConcreteClassProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ConcreteClassArgumentResolver.class)
    public ConcreteClassArgumentResolver concreteClassArgumentResolver(@Autowired(required = false) HandlerMethodArgumentConsumer consumer) {
        Map<String, String> concreteClasses = properties.getConcreteClasses();
        Map<Class, Class> classes = concreteClasses.entrySet().stream().collect(
                Collectors.toMap(entry -> forName(entry.getKey()), entry -> forName(entry.getValue()))
        );
        return new ConcreteClassArgumentResolver(classes, consumer);
    }

    private static Class forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(className);
        }
    }

    @Bean
    public WebMvcConfigurer concreteClassWebMvcConfigurer(ConcreteClassArgumentResolver concreteClassArgumentResolver) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(0, concreteClassArgumentResolver);
            }
        };
    }
}
