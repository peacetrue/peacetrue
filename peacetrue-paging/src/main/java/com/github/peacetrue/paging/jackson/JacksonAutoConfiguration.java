package com.github.peacetrue.paging.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.peacetrue.paging.PageAttribute;
import com.github.peacetrue.paging.PagingProperties;
import com.github.peacetrue.paging.converter.GenericPageConverter;
import com.github.peacetrue.paging.converter.PageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * auto configuration for jackson
 *
 * @author xiayx
 */
@ConditionalOnClass(ObjectMapper.class)
@ImportAutoConfiguration(JacksonAutoConfiguration.SpringAutoConfiguration.class)
public class JacksonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @ConditionalOnClass(Page.class)
    public static class SpringAutoConfiguration {

        @Autowired
        private PagingProperties pagingProperties;

        @Bean
        @ConditionalOnMissingBean(PageConverter.class)
        public PageConverter pageConverter() {
            GenericPageConverter converter = new GenericPageConverter();
            HashMap<PageAttribute, String> source = new HashMap<>();
            source.put(PageAttribute.data, "content");
            converter.setSource(source);
            converter.setTarget(pagingProperties.getTarget());
            return converter;
        }

        @Autowired
        public void registerPageSerializer(ObjectMapper objectMapper, PageConverter<? super Page> pageConverter) {
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Page.class, new PageSerializer<>(pageConverter));
            objectMapper.registerModule(simpleModule);
        }
    }
}
