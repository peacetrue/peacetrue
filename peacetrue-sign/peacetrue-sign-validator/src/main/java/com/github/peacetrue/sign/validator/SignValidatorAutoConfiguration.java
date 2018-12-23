package com.github.peacetrue.sign.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.CachedSecretProvider;
import com.github.peacetrue.sign.Md5SignGenerator;
import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.sign.SignGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 接收请求时验证参数签名
 *
 * @author xiayx
 * @see com.github.peacetrue.sign.appender.SignAppendAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(SignValidatorProperties.class)
public class SignValidatorAutoConfiguration {

    private SignValidatorProperties properties;

    public SignValidatorAutoConfiguration(SignValidatorProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean(SecretProvider.class)
    public SecretProvider secretProvider() {
        return new CachedSecretProvider(properties.getServer());
    }

    @Bean
    @ConditionalOnMissingBean(SignGenerator.class)
    public SignGenerator signGenerator() {
        return new Md5SignGenerator();
    }

    /** 用于验证表单参数 */
    @Bean
    @ConditionalOnMissingBean(name = "formValidatorHandlerInterceptor")
    public FormValidatorHandlerInterceptor formValidatorHandlerInterceptor() {
        return new FormValidatorHandlerInterceptor();
    }

    /** 用于注册表单验证器 */
    @Bean
    public WebMvcConfigurer formValidatorWebMvcConfigurer(HandlerInterceptor formValidatorHandlerInterceptor) {
        return new WebMvcConfigurerAdapter() {
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(formValidatorHandlerInterceptor).addPathPatterns("/**");
            }
        };
    }

    /** 用于验证表单参数 */
    @Bean
    @ConditionalOnMissingBean(name = "formValidator")
    public FormValidator formValidator() {
        return new FormValidator();
    }

    /** 用于验证原始参数 */
    @Bean
    @ConditionalOnMissingBean(name = "rawValidatorRequestBodyAdvice")
    public RawValidatorRequestBodyAdvice rawValidatorRequestBodyAdvice() {
        return new RawValidatorRequestBodyAdvice();
    }

    /** 用于验证原始参数 */
    @Bean
    @ConditionalOnMissingBean(name = "rawValidator")
    public RawValidator rawValidator() {
        return new RawValidator();
    }

}
