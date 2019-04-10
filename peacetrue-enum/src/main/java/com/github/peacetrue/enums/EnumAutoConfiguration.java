package com.github.peacetrue.enums;

import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(EnumProperties.class)
public class EnumAutoConfiguration {

    private EnumProperties enumProperties;

    public EnumAutoConfiguration(EnumProperties enumProperties) {
        this.enumProperties = enumProperties;
    }

    /** 在指定包下搜索枚举类 */
    private static Map<String, Class<? extends Enum>> resolveEnumClasses(EnumNameResolver resolver, String... basePackagePaths) {
        if (ObjectUtils.isEmpty(basePackagePaths)) return Collections.emptyMap();

        Map<String, Class<? extends Enum>> enumClasses = new HashMap<>();
        Arrays.stream(basePackagePaths).forEach(path -> {
            Reflections reflections = new Reflections(path);
            Set<Class<? extends Enum>> enumSubClasses = reflections.getSubTypesOf(Enum.class);
            enumSubClasses.forEach(enumClass -> enumClasses.put(resolver.resolveEnumName(enumClass), enumClass));
        });
        return enumClasses;
    }

    /** 将枚举类转换成枚举值数组 */
    private static Map<String, Enum[]> toEnumArray(Map<String, Class<? extends Enum>> enumClasses) {
        return enumClasses.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getEnumConstants()));
    }

    @Bean
    public EnumController enumController() {
        Map<String, Class<? extends Enum>> enumClasses = new HashMap<>(
                resolveEnumClasses(enumNameResolver(), enumProperties.getBasePackagePaths())
        );
        enumClasses.putAll(enumProperties.getEnumClasses());
        return new EnumController(toEnumArray(enumClasses));
    }

    @Bean
    @ConditionalOnMissingBean(EnumNameResolver.class)
    public EnumNameResolver enumNameResolver() {
        return enumClass -> StringUtils.uncapitalize(enumClass.getSimpleName());
    }

}
