package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import com.github.peacetrue.printer.MessageSourceClassPrinter;
import com.github.peacetrue.result.exception.GenericExceptionHandler;
import com.github.peacetrue.result.exception.converters.*;
import com.github.peacetrue.result.exception.converters.jackson.InvalidFormatExceptionConverter;
import com.github.peacetrue.result.exception.converters.jackson.JsonParseExceptionConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
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

    @Bean
    @ConditionalOnMissingBean(name = "invalidFormatExceptionConverter")
    public InvalidFormatExceptionConverter invalidFormatExceptionConverter() {
        return new InvalidFormatExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "jsonParseExceptionConverter")
    public JsonParseExceptionConverter jsonParseExceptionConverter() {
        return new JsonParseExceptionConverter();
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

    @Bean
    @ConditionalOnMissingBean(LocaleResolver.class)
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        localeResolver.setCookieName("localeResolver");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    public static class ResultForm {

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
    @ConditionalOnMissingBean(name = "resultJsonHttpMessageConverter")
    public ResultJackson2HttpMessageConverter resultJsonHttpMessageConverter() {
        return new ResultJackson2HttpMessageConverter();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(GenericHttpMessageConverter<?> resultJsonHttpMessageConverter) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                for (int i = 0; i < converters.size(); i++) {
                    HttpMessageConverter<?> httpMessageConverter = converters.get(i);
                    if (httpMessageConverter.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON)
                            && httpMessageConverter instanceof GenericHttpMessageConverter) {
                        if (resultJsonHttpMessageConverter instanceof ResultJackson2HttpMessageConverter) {
                            ((ResultJackson2HttpMessageConverter) resultJsonHttpMessageConverter)
                                    .setHttpMessageConverter((GenericHttpMessageConverter) httpMessageConverter);
                        }
                        converters.set(i, resultJsonHttpMessageConverter);
                        break;
                    }
                }
            }
        };
    }
}
