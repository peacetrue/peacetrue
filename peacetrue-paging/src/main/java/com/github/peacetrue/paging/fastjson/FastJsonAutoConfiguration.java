package com.github.peacetrue.paging.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.github.peacetrue.paging.converter.PageConverter;
import com.github.peacetrue.paging.spring.SpringAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * auto configuration for FastJson
 *
 * @author xiayx
 */
@ConditionalOnClass(JSON.class)
@ImportAutoConfiguration(FastJsonAutoConfiguration.FastJsonSpringAutoConfiguration.class)
public class FastJsonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FastJsonConfig.class)
    public FastJsonConfig fastJsonConfig() {
        return new FastJsonConfig();
    }

    @ConditionalOnClass(Page.class)
    @AutoConfigureAfter(SpringAutoConfiguration.class)
    public static class FastJsonSpringAutoConfiguration {

        @Autowired
        public void registerPageSerializer(FastJsonConfig fastJsonConfig, PageConverter<Object> springPageConverter) {
            fastJsonConfig.getSerializeConfig().put(PageImpl.class, new PageSerializer(springPageConverter));
        }
    }
}
