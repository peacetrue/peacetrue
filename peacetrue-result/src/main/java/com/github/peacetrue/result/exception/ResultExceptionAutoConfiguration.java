package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultAutoConfiguration;
import com.github.peacetrue.result.exception.converters.ConditionalExceptionConverter;
import com.github.peacetrue.result.exception.converters.FallbackExceptionConverter;
import com.github.peacetrue.result.exception.converters.ProxyConditionalExceptionConverter;
import com.github.peacetrue.result.exception.converters.ResultExceptionConverter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static com.github.peacetrue.result.exception.StandardExceptionConvertService.FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME;

/**
 * for exception convert
 */
@AutoConfigureAfter(ResultAutoConfiguration.BuilderAutoConfiguration.class)
@EnableConfigurationProperties(ResultExceptionProperties.class)
public class ResultExceptionAutoConfiguration {

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


}
