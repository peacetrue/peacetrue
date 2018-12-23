package com.github.peacetrue.sign.decode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.CachedSecretProvider;
import com.github.peacetrue.sign.SecretProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * the AutoConfiguration for server sign
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(SignDecodeProperties.class)
public class SignDecodeAutoConfiguration {

    private SignDecodeProperties properties;

    public SignDecodeAutoConfiguration(SignDecodeProperties properties) {
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

    /** 验证请求参数 */
    @Bean
    @ConditionalOnMissingBean(name = "signValidHandlerInterceptor")
    public SignValidHandlerInterceptor signValidHandlerInterceptor() {
        return new SignValidHandlerInterceptor();
    }

    /** 验证请求参数 */
    @Bean
    @ConditionalOnMissingBean(name = "paramSignValidator", value = SignValidator.class)
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
    @ConditionalOnMissingBean(name = "bodySignValidator", value = SignValidator.class)
    public BodySignValidator bodySignValidator() {
        return new BodySignValidator();
    }

}
