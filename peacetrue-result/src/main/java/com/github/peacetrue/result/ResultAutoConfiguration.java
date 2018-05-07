package com.github.peacetrue.result;

import com.github.peacetrue.result.exception.GenericExceptionHandler;
import com.github.peacetrue.result.exception.converters.*;
import com.github.peacetrue.result.printer.ClassPrinter;
import com.github.peacetrue.result.printer.MessageSourceClassPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.Locale;

/**
 * the AutoConfiguration class for Result
 *
 * @author xiayx
 */
@EnableConfigurationProperties(ResultProperties.class)
public class ResultAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ResultProperties resultProperties;

    public ResultAutoConfiguration(ResultProperties resultProperties) {
        logger.debug("got ResultProperties: {}", resultProperties);
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
    @ConditionalOnMissingBean(name = "genericExceptionHandler")
    public GenericExceptionHandler genericExceptionHandler() {
        return new GenericExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "genericExceptionConverter")
    public GenericExceptionConverter genericExceptionConverter() {
        return new GenericExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(ResultCodeResolver.class)
    public ResultCodeResolver resultCodeResolver() {
        SimpleResultCodeResolver resolver = new SimpleResultCodeResolver();
        resolver.setCodes(resultProperties.getCodes());
        return resolver;
    }

    @Bean
    @ConditionalOnMissingBean(name = "defaultExceptionConverter")
    public DefaultExceptionConverter defaultExceptionConverter() {
        return new DefaultExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "resultExceptionConverter")
    public ResultExceptionConverter resultExceptionConverter() {
        return new ResultExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "methodArgumentTypeMismatchExceptionConverter")
    public MethodArgumentTypeMismatchExceptionConverter methodArgumentTypeMismatchExceptionConverter() {
        return new MethodArgumentTypeMismatchExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "bindExceptionConverter")
    public BindExceptionConverter bindExceptionConverter() {
        return new BindExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "methodArgumentNotValidExceptionConverter")
    public MethodArgumentNotValidExceptionConverter methodArgumentNotValidExceptionConverter() {
        return new MethodArgumentNotValidExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "httpMessageNotReadableExceptionConverter")
    public HttpMessageNotReadableExceptionConverter httpMessageNotReadableExceptionConverter() {
        return new HttpMessageNotReadableExceptionConverter();
    }

    public static class MessageSourceConfiguration {

        @Bean
        @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
        @ConditionalOnMissingBean(MessageSource.class)
        public MessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setDefaultEncoding("UTF-8");
            messageSource.setBasenames("com.github.peacetrue.result.messages", "messages");
//            messageSource.setCacheSeconds(10); //reload messages every 10 seconds
            return messageSource;
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

    }

    public static class ResultFormConfiguration {

        @Autowired
        private ResultProperties resultProperties;

        /** copy from {@link WebMvcAutoConfigurationAdapter#viewResolver(BeanFactory)} */
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
        public void setContentNegotiatingViewResolver(ContentNegotiatingViewResolver viewResolver) {
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

}
