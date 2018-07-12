package com.github.peacetrue.result;

import com.github.peacetrue.result.exception.ExceptionConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.List;

/**
 * used in web environment,
 * support content negotiate and auto result return
 *
 * @author xiayx
 */
@AutoConfigureAfter({
        ResultBuilderAutoConfiguration.class,
        WebMvcAutoConfiguration.EnableWebMvcConfiguration.class
})
@EnableConfigurationProperties(ResultWebProperties.class)
public class ResultWebAutoConfiguration {

    private ResultWebProperties resultWebProperties;

    public ResultWebAutoConfiguration(ResultWebProperties resultWebProperties) {
        this.resultWebProperties = resultWebProperties;
    }

    // TODO 不明白为什么需要额外使用一个配置类，如果直接放在外层配置类中，
    // 导致异常【java.lang.IllegalArgumentException: A ServletContext is required to configure default servlet handling】
    @Configuration
    public static class ViewResolverConfiguration {
        @Autowired
        public void initDefaultView(ContentNegotiatingViewResolver viewResolver) {
            viewResolver.setDefaultViews(Collections.singletonList(jsonView()));
        }

        @Bean
        @ConditionalOnMissingBean(name = "jsonView")
        public MappingJackson2JsonView jsonView() {
            return new MappingJackson2JsonView();
        }

    }

    @Autowired
    public void initResultWebUtils(ResultBuilder resultBuilder) {
        ResultWebUtils.setResultBuilder(resultBuilder);
    }

    @ConditionalOnBean(ExceptionConvertService.class)
    public static class ExceptionConfiguration {
        @Bean
        @ConditionalOnMissingBean(name = "exceptionHandler")
        public GenericExceptionHandler exceptionHandler() {
            return new GenericExceptionHandler();
        }
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionPageResolver.class)
    public ExceptionPageResolver exceptionPageResolver() {
        SimpleExceptionPageResolver resolver = new SimpleExceptionPageResolver();
        resolver.setExceptionPage(resultWebProperties.getExceptionPage());
        return resolver;
    }

    @Bean
    @ConditionalOnMissingBean(name = "resultResponseBodyAdvice", value = ResponseBodyAdvice.class)
    public ResultResponseBodyAdvice resultResponseBodyAdvice() {
        ResultResponseBodyAdvice advice = new ResultResponseBodyAdvice();
        advice.setExcludes(resultWebProperties.getExcludeAutoConvertWhenReturn());
        return advice;
    }

    @Bean
    @ConditionalOnMissingBean(name = "resultBeanPostProcessor")
    public BeanPostProcessor resultBeanPostProcessor(ResultResponseBodyAdvice resultResponseBodyAdvice) {
        return new ResultBeanPostProcessor(resultResponseBodyAdvice);
    }

    @Bean
    public WebMvcConfigurer jacksonAtFirstWebMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.sort((o1, o2) -> {
                    if (o1 instanceof AbstractJackson2HttpMessageConverter) return -1;
                    if (o2 instanceof AbstractJackson2HttpMessageConverter) return 1;
                    return 0;
                });
            }
        };
    }
}
