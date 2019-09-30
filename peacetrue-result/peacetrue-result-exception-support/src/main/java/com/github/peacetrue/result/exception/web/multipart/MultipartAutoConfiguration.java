package com.github.peacetrue.result.exception.web.multipart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiayx
 */
@Configuration
public class MultipartAutoConfiguration {

    @Bean
    public FileSizeLimitExceededExceptionConverter fileSizeLimitExceededExceptionConverter() {
        return new FileSizeLimitExceededExceptionConverter();
    }

    @Bean
    public MaxUploadSizeExceededExceptionConverter maxUploadSizeExceededExceptionConverter() {
        return new MaxUploadSizeExceededExceptionConverter();
    }

    @Bean
    public MultipartExceptionConverter multipartExceptionConverter() {
        return new MultipartExceptionConverter();
    }

    @Bean
    public MissingServletRequestPartExceptionConverter missingServletRequestPartExceptionConverter() {
        return new MissingServletRequestPartExceptionConverter();
    }
}
