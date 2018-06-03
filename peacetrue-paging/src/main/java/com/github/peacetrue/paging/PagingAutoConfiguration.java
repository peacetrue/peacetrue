package com.github.peacetrue.paging;

import com.github.peacetrue.paging.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * the auto configuration for paging
 *
 * @author xiayx
 */
@EnableConfigurationProperties(PagingProperties.class)
@ImportAutoConfiguration(JacksonAutoConfiguration.class)
public class PagingAutoConfiguration {

}
