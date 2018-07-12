package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.exception.net.UnknownHostExceptionConverter;
import org.springframework.context.annotation.Bean;

/**
 * for basic exception
 *
 * @author xiayx
 */
public class ResultBasicExceptionAutoConfiguration {

    @Bean
    public UnknownHostExceptionConverter unknownHostExceptionConverter() {
        return new UnknownHostExceptionConverter();
    }
}
