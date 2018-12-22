package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultBuilderAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import static com.github.peacetrue.result.exception.StandardExceptionConvertService.FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME;

/**
 * used to handler exception
 *
 * @author xiayx
 */
@AutoConfigureAfter(ResultBuilderAutoConfiguration.class)
@EnableConfigurationProperties(ResultExceptionProperties.class)
public class ResultExceptionAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ResultExceptionProperties resultExceptionProperties;

    public ResultExceptionAutoConfiguration(ResultExceptionProperties resultExceptionProperties) {
        this.resultExceptionProperties = resultExceptionProperties;
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionConvertService.class)
    public ExceptionConvertService exceptionConvertService() {
        return new StandardExceptionConvertService();
    }

    @Bean
    @ConditionalOnMissingBean(name = FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME)
    public FallbackExceptionConverter fallbackExceptionConverter() {
        return new FallbackExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "resultExceptionConverter")
    public ResultExceptionConverter resultExceptionConverter() {
        return new ResultExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "proxyConditionalExceptionConverter")
    public ConditionalExceptionConverter proxyConditionalExceptionConverter() {
        return new ProxyConditionalExceptionConverter(resultExceptionProperties.getProxyClasses());
    }

    @Bean
    @ConditionalOnMissingBean(name = "noArgsConditionalExceptionConverter")
    public NoArgsConditionalExceptionConverter noArgsConditionalExceptionConverter() {
        return new NoArgsConditionalExceptionConverter(resultExceptionProperties.getNoArgsClasses());
    }

    @Autowired
    public void addResultExceptionMessage(MessageSource messageSource) {
        //add default result exception messages config
        if (messageSource instanceof ResourceBundleMessageSource) {
            ((ResourceBundleMessageSource) messageSource).addBasenames("result-exception");
        } else {
            logger.warn("the default config 'result-exception.properties' not be added to MessageSource");
        }
    }

}
