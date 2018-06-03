package com.github.peacetrue.paging;

import com.github.peacetrue.paging.fastjson.FastJsonAutoConfiguration;
import com.github.peacetrue.paging.jackson.JacksonAutoConfiguration;
import com.github.peacetrue.paging.spring.SpringAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * the auto configuration for paging
 *
 * @author xiayx
 */
@EnableConfigurationProperties(PagingProperties.class)
@ImportAutoConfiguration({
        SpringAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        FastJsonAutoConfiguration.class,
})
public class PagingAutoConfiguration {

}
