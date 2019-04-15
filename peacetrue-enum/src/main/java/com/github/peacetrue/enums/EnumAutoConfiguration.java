package com.github.peacetrue.enums;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(EnumProperties.class)
public class EnumAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private EnumProperties enumProperties;

    public EnumAutoConfiguration(EnumProperties enumProperties) {
        this.enumProperties = enumProperties;
    }

    /** 在指定包下搜索枚举类 */
    static Set<Class<? extends Enum>> findEnumClasses(String... basePackagePaths) {
        if (ObjectUtils.isEmpty(basePackagePaths)) return Collections.emptySet();

        return Arrays.stream(basePackagePaths)
                .flatMap(path -> new Reflections(path).getSubTypesOf(Enum.class).stream())
                .filter(enumClass -> enumClass.getSuperclass().equals(Enum.class))//防止找到非枚举的子类
                .collect(Collectors.toSet());
    }

    /** 命名枚举类 */
    static Map<String, Class<? extends Enum>> nameEnumClasses(Set<Class<? extends Enum>> enumClasses, EnumNameResolver resolver) {
        if (ObjectUtils.isEmpty(enumClasses)) return Collections.emptyMap();
        return enumClasses.stream().collect(Collectors.toMap(resolver::resolveEnumName, Function.identity()));
    }

    /** 将枚举类转换成枚举值数组 */
    static Map<String, Enum[]> toEnumArray(Map<String, Class<? extends Enum>> enumClasses) {
        return enumClasses.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getEnumConstants()));
    }

    private Map<String, Enum[]> resolveEnums() {
        try {
            Set<Class<? extends Enum>> enumClasses = findEnumClasses(enumProperties.getBasePackagePaths());
            Map<String, Class<? extends Enum>> enumClassMap = nameEnumClasses(enumClasses, enumNameResolver());
            if (!enumProperties.getEnumClasses().isEmpty()) {
                enumClassMap = new HashMap<>(enumClassMap);
                enumClassMap.putAll(enumProperties.getEnumClasses());
            }
            return toEnumArray(enumClassMap);
        } catch (Exception e) {
            logger.error("解析枚举类异常", e);
            return Collections.emptyMap();
        }
    }

    @Bean
    public EnumController enumController() {
        return new EnumController(resolveEnums());
    }

    @Bean
    @ConditionalOnMissingBean(EnumNameResolver.class)
    public EnumNameResolver enumNameResolver() {
        return new SimpleEnumNameResolver();
    }

}
