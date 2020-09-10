package com.github.peacetrue.spring.staticresources;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : xiayx
 * @since : 2020-07-02 23:04
 **/
@Data
@ConfigurationProperties("peacetrue")
public class StaticResourceProperties {

    /** 静态资源配置，key 表示路径规则，value 表示转发地址 */
    private Map<String, String> staticResources = new LinkedHashMap<>();

}
