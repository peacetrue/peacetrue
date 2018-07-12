package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.FailureType;
import com.github.peacetrue.result.ResultBuilderProperties;
import com.github.peacetrue.result.exception.ExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * for web exception convert
 *
 * @author xiayx
 */
@ConditionalOnClass(WebMvcConfigurer.class)
public class ResultWebExceptionAutoConfiguration {

    @Bean
    public ExceptionConverter missingServletRequestParameterExceptionConverter() {
        return new MissingServletRequestParameterExceptionConverter();
    }

    @Bean
    public ExceptionConverter missingPathVariableExceptionConverter() {
        return new MissingPathVariableExceptionConverter();
    }

    @Bean
    public ExceptionConverter methodArgumentTypeMismatchExceptionConverter() {
        return new MethodArgumentTypeMismatchExceptionConverter();
    }

    @Bean
    public ExceptionConverter methodArgumentConversionNotSupportedExceptionConverter() {
        return new MethodArgumentConversionNotSupportedExceptionConverter();
    }

    @Bean
    public ExceptionConverter bindExceptionConverter() {
        return new BindExceptionConverter();
    }

    @Bean
    public ExceptionConverter methodArgumentNotValidExceptionConverter() {
        return new MethodArgumentNotValidExceptionConverter();
    }

//    @Bean
//    @ConditionalOnMissingBean(name = "resourceAccessExceptionConverter")
//    public ResourceAccessExceptionConverter resourceAccessExceptionConverter() {
//        return new ResourceAccessExceptionConverter();
//    }

    @Autowired
    public void addCodes(ResultBuilderProperties properties) {
        properties.addCode("MissingServletRequestParameterException", FailureType.parameter_missing.name());
        properties.addCode("MissingPathVariableException", FailureType.server_error.name());
        properties.addCode("MethodArgumentTypeMismatchException", FailureType.parameter_type_mismatch.name());
        properties.addCode("MethodArgumentConversionNotSupportedException", FailureType.server_error.name());
        properties.addCode("BindException", FailureType.parameter_error.name());
    }

}
