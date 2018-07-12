package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.exception.ExceptionConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * auto configuration for jackson exception
 *
 * @author xiayx
 */
@ConditionalOnClass(ObjectMapper.class)
public class ResultJacksonExceptionAutoConfiguration {

    @Bean
    public ExceptionConverter jsonParseExceptionConverter() {
        return new JsonParseExceptionConverter();
    }

    @Bean
    public ExceptionConverter invalidFormatExceptionConverter() {
        return new InvalidFormatExceptionConverter();
    }
}
