package com.github.peacetrue.spring.boot;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * think class annotated with {@link SpringBootApplication}
 * or {@link SpringBootConfiguration} is base package
 *
 * @author xiayx
 */
public class BootBasePackageProvider implements BasePackageProvider {

    @Autowired
    private ListableBeanFactory beanFactory;

    public String getBasePackage() {
        String basePackage = getBasePackage(SpringBootApplication.class);
        if (basePackage == null) basePackage = getBasePackage(SpringBootConfiguration.class);
        if (basePackage == null) throw new IllegalStateException("can't found the class annotated with @SpringBootApplication or @SpringBootConfiguration");
        return basePackage;
    }

    private String getBasePackage(Class<? extends Annotation> annotationType) {
        Map<String, Object> candidates = beanFactory.getBeansWithAnnotation(annotationType);
        return candidates.isEmpty() ? null : candidates.values().iterator().next().getClass().getPackage().getName();
    }

}
