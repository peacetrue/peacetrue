package com.github.peacetrue.enums;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.enum")
public class EnumProperties {

    /** 枚举控制器映射路径 */
    private String mapping = "/enums";
    /** 枚举类搜索的基础类路径 */
    private String[] basePackagePaths;
    /** 枚举名称到类的映射 */
    private Map<String, Class<? extends Enum>> enumClasses = new HashMap<>();

}
