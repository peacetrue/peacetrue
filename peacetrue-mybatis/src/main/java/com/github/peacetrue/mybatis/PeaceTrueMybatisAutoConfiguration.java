package com.github.peacetrue.mybatis;

import com.github.peacetrue.mybatis.spring.RowBoundsHandlerMethodArgumentResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * auto configuration for mybatis
 *
 * @author xiayx
 */
@EnableConfigurationProperties(MybatisProperties.class)
public class PeaceTrueMybatisAutoConfiguration {

    private MybatisProperties mybatisProperties;

    public PeaceTrueMybatisAutoConfiguration(MybatisProperties mybatisProperties) {
        this.mybatisProperties = mybatisProperties;
    }

    public HandlerMethodArgumentResolver rowBoundsHandlerMethodArgumentResolver() {
        RowBoundsHandlerMethodArgumentResolver resolver = new RowBoundsHandlerMethodArgumentResolver();
        resolver.setLimitParameterName(mybatisProperties.getLimitParameterName());
        resolver.setOffsetParameterName(mybatisProperties.getOffsetParameterName());
        resolver.setDefaultOffsetParameterValue(mybatisProperties.getDefaultOffsetParameterValue());
        resolver.setDefaultSizeParameterValue(mybatisProperties.getDefaultSizeParameterValue());
        return resolver;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(rowBoundsHandlerMethodArgumentResolver());
            }
        };
    }
}
