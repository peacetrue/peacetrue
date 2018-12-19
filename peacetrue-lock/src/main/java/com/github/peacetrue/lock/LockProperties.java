package com.github.peacetrue.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.lock")
public class LockProperties {

    /** redis 键名前缀 */
    private String prefix = "peacetrue:";
    /** redis 自动过期时间(毫秒) */
    private long expiry = 10 * 1000;

}
