package com.github.peacetrue.paging.spring;

import com.github.peacetrue.paging.PageAttribute;
import com.github.peacetrue.paging.PagingProperties;
import com.github.peacetrue.paging.converter.GenericPageConverter;
import com.github.peacetrue.paging.converter.PageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * auto configuration for spring
 *
 * @author xiayx
 */
@ConditionalOnClass(Page.class)
public class SpringAutoConfiguration {

    @Autowired
    private PagingProperties pagingProperties;

    @Bean
    @ConditionalOnMissingBean(value = PageConverter.class, name = "springPageConverter")
    public PageConverter pageConverter() {
        GenericPageConverter converter = new GenericPageConverter();
        Map<PageAttribute, String> source = new HashMap<>();
        source.put(PageAttribute.data, "content");
        converter.setSource(source);
        if (pagingProperties.getTarget() != null) {
            converter.setTarget(pagingProperties.getTarget());
        }
        return converter;
    }

}
