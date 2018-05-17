package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import com.github.peacetrue.printer.MessageSourceClassPrinter;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.ResultWebExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.mysql.ResultMysqlExceptionAutoConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.springframework.context.support.AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME;

/**
 * the AutoConfiguration class for Result
 *
 * @author xiayx
 */
@ImportAutoConfiguration({
        ResultExceptionAutoConfiguration.class,
        ResultWebExceptionAutoConfiguration.class,
        ResultMysqlExceptionAutoConfiguration.class
})
public class ResultAutoConfiguration {

    @Autowired
    private ResultProperties resultProperties;

    /**
     * for result build
     */
    @EnableConfigurationProperties(ResultProperties.class)
    public static class BuilderAutoConfiguration {

        private ResultProperties resultProperties;

        public BuilderAutoConfiguration(ResultProperties resultProperties) {
            this.resultProperties = resultProperties;
        }

        @Bean
        @ConditionalOnMissingBean(ResultBuilder.class)
        public ResultBuilder resultBuilder() {
            MessageSourceResultBuilder builder = new MessageSourceResultBuilder();
            builder.setPrefix(resultProperties.getCodePrefix());
            ResultUtils.setResultBuilder(builder);
            return builder;
        }

        @Bean
        @ConditionalOnMissingBean(ClassPrinter.class)
        public ClassPrinter classPrinter() {
            MessageSourceClassPrinter printer = new MessageSourceClassPrinter();
            printer.setPrefix(resultProperties.getClassPrefix());
            return printer;
        }

        @Bean
        @ConditionalOnMissingBean(ResultCodeResolver.class)
        public ResultCodeResolver resultCodeResolver() {
            SimpleResultCodeResolver resolver = new SimpleResultCodeResolver();
            resolver.setCodes(resultProperties.getCodes());
            return resolver;
        }

        @Bean
        @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
        @ConditionalOnMissingBean(name = MESSAGE_SOURCE_BEAN_NAME, value = MessageSource.class)
        public MessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setDefaultEncoding("UTF-8");
            messageSource.setBasenames("com.github.peacetrue.result.messages", "messages");
            return messageSource;
        }

    }

    @Bean
    @ConditionalOnMissingBean(LocaleResolver.class)
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        localeResolver.setCookieName("localeResolver");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }


    /**
     * for result view
     */
    public static class ViewAutoConfiguration {

        @Autowired
        private ResultProperties resultProperties;

        //copy from {@link WebMvcAutoConfigurationAdapter#viewResolver(BeanFactory)}
        @Bean
        @ConditionalOnBean(ViewResolver.class)
        @ConditionalOnMissingBean(name = "viewResolver", value = ContentNegotiatingViewResolver.class)
        public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
            ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
            resolver.setContentNegotiationManager(
                    beanFactory.getBean(ContentNegotiationManager.class));
            // ContentNegotiatingViewResolver uses all the other view resolvers to locate
            // a view so it should have a high precedence
            resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return resolver;
        }


        @Autowired
        public void initDefaultView(ContentNegotiatingViewResolver viewResolver) {
            viewResolver.setDefaultViews(Collections.singletonList(jsonView()));
        }

        @Bean
        @ConditionalOnMissingBean(name = "jsonView")
        public MappingJackson2JsonView jsonView() {
            return new MappingJackson2JsonView();
        }

        @Bean
        @ConditionalOnMissingBean(ErrorPageResolver.class)
        public ErrorPageResolver errorPageResolver() {
            SimpleErrorPageResolver resolver = new SimpleErrorPageResolver();
            resolver.setErrorPage(resultProperties.getErrorPage());
            return resolver;
        }

    }

    @Bean
    @ConditionalOnMissingBean(name = "resultResponseBodyAdvice", value = ResponseBodyAdvice.class)
    public ResultResponseBodyAdvice resultResponseBodyAdvice() {
        ResultResponseBodyAdvice advice = new ResultResponseBodyAdvice();
        advice.setExcludes(resultProperties.getExcludeAutoConvertWhenReturn());
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
