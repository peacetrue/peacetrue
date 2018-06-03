package com.github.peacetrue.paging.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.peacetrue.paging.converter.PageConverter;
import com.github.peacetrue.paging.spring.SpringAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

/**
 * auto configuration for jackson
 *
 * @author xiayx
 */
@ConditionalOnClass(ObjectMapper.class)
@ImportAutoConfiguration(JacksonAutoConfiguration.JacksonSpringAutoConfiguration.class)
public class JacksonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @ConditionalOnClass(Page.class)
    @AutoConfigureAfter(SpringAutoConfiguration.class)
    public static class JacksonSpringAutoConfiguration {

        @Autowired
        public void registerPageSerializer(ObjectMapper objectMapper, PageConverter<? super Page> springPageConverter) {
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Page.class, new PageSerializer<>(springPageConverter));
            objectMapper.registerModule(simpleModule);
        }
    }
}
