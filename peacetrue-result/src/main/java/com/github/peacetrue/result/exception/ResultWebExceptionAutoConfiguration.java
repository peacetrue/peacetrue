package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.exception.converters.BindExceptionConverter;
import com.github.peacetrue.result.exception.converters.MethodArgumentNotValidExceptionConverter;
import com.github.peacetrue.result.exception.converters.MethodArgumentTypeMismatchExceptionConverter;
import com.github.peacetrue.result.exception.converters.jackson.InvalidFormatExceptionConverter;
import com.github.peacetrue.result.exception.converters.jackson.JsonParseExceptionConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * for web exception convert
 */
public class ResultWebExceptionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "genericExceptionHandler")
    public GenericExceptionHandler genericExceptionHandler() {
        return new GenericExceptionHandler();
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
    @ConditionalOnMissingBean(name = "invalidFormatExceptionConverter")
    public InvalidFormatExceptionConverter invalidFormatExceptionConverter() {
        return new InvalidFormatExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "jsonParseExceptionConverter")
    public JsonParseExceptionConverter jsonParseExceptionConverter() {
        return new JsonParseExceptionConverter();
    }
}
