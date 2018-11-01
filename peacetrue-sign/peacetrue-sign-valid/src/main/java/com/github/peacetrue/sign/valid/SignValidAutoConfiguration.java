package com.github.peacetrue.sign.valid;

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
 * @see com.github.peacetrue.sign.append.SignAppendAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(SignValidProperties.class)
public class SignValidAutoConfiguration {

    private SignValidProperties properties;

    public SignValidAutoConfiguration(SignValidProperties properties) {
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

    /** 验证请求参数 */
    @Bean
    @ConditionalOnMissingBean(name = "signValidHandlerInterceptor")
    public SignValidHandlerInterceptor signValidHandlerInterceptor() {
        return new SignValidHandlerInterceptor();
    }

    @Bean
    public WebMvcConfigurer signValidWebMvcConfigurer(HandlerInterceptor signValidHandlerInterceptor) {
        return new WebMvcConfigurer() {
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(signValidHandlerInterceptor).addPathPatterns("/**");
            }
        };
    }

    /** 验证请求参数 */
    @Bean
    @ConditionalOnMissingBean(name = "paramSignValidator")
    public ParamSignValidator paramSignValidator() {
        return new ParamSignValidator();
    }

    /** 验证请求体 */
    @Bean
    @ConditionalOnMissingBean(name = "signValidRequestBodyAdvice")
    public SignValidRequestBodyAdvice signValidRequestBodyAdvice() {
        return new SignValidRequestBodyAdvice();
    }

    /** 验证请求体 */
    @Bean
    @ConditionalOnMissingBean(name = "bodySignValidator")
    public BodySignValidator bodySignValidator() {
        return new BodySignValidator();
    }

}
