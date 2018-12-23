package com.github.peacetrue.sign.appender;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.Md5SignGenerator;
import com.github.peacetrue.sign.SignGenerator;
import com.github.peacetrue.sign.URIApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.Arrays;

/**
 * 发送请求时追加参数签名
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(SignAppenderProperties.class)
public class SignAppenderAutoConfiguration {

    private SignAppenderProperties properties;

    public SignAppenderAutoConfiguration(SignAppenderProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Configuration
    public static class ConversionServiceConfiguration {
        @Bean
        @ConditionalOnMissingBean(ConversionService.class)
        public ConversionService conversionService() {
            return new DefaultFormattingConversionService();
        }
    }

    @Bean
    @ConditionalOnMissingBean(SignGenerator.class)
    public SignGenerator signGenerator() {
        return new Md5SignGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(URIApp.class)
    public URIApp uriApp() {
        return properties.getClient();
    }

    /** 用于客户端向服务端发送请求 */
    @Bean
    @ConditionalOnMissingBean(name = "appenderRestTemplate")
    public AppenderRestTemplate appenderRestTemplate(URIApp uriApp,
                                                     AppenderHttpMessageConverters converters,
                                                     ClientHttpRequestInterceptor signClientHttpRequestInterceptor) {
        AppenderRestTemplate restTemplate = new AppenderRestTemplate(
                uriApp.getUri(),
                Arrays.asList(converters.getForm(), converters.getRaw()));
        restTemplate.getInterceptors().add(signClientHttpRequestInterceptor);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(AppenderHttpMessageConverters.class)
    public AppenderHttpMessageConverters appenderHttpMessageConverters() {
        return new AppenderHttpMessageConvertersImpl();
    }

    /** 用于签名原始参数 */
    @Bean
    @ConditionalOnMissingBean(name = "rawSignService")
    public SignService rawSignService() {
        return new RawSignService();
    }

    /** 用于签名表单参数 */
    @Bean
    @ConditionalOnMissingBean(name = "formSignService")
    public SignService formSignService() {
        return new FormSignService();
    }

    /** 用于签名链接参数 */
    @Bean
    @ConditionalOnMissingBean(name = "signClientHttpRequestInterceptor")
    public ClientHttpRequestInterceptor signClientHttpRequestInterceptor() {
        return new SignClientHttpRequestInterceptor();
    }


//    @Bean
//    @ConditionalOnMissingBean(UriRestTemplateFactory.class)
//    public UriRestTemplateFactory uriRestTemplateFactory() {
//        return new ClientUriRestTemplateFactory();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(name = "uriRestTemplate")
//    public UriRestTemplate uriRestTemplate(UriRestTemplateFactory uriRestTemplateFactory) {
//        return uriRestTemplateFactory.getUriRestTemplate(properties.getClient());
//    }

//    @Bean
//    @ConditionalOnMissingBean(SecretProvider.class)
//    public SecretProvider secretProvider() {
//        return new CachedSecretProvider(properties.getServer());
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(ProviderClientSignService.class)
//    public ProviderClientSignService providerClientSignService() {
//        return new ProviderClientSignService();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(name = "providerRestTemplate")
//    public RestTemplate providerRestTemplate(HttpMessageConverter<Object> encodeHttpMessageConverter) {
//        SignHttpMessageConverter messageConverter = new SignHttpMessageConverter();
//        messageConverter.setClientSignService(providerClientSignService());
//        messageConverter.setHttpMessageConverter(encodeHttpMessageConverter);
//        return new RestTemplate(Collections.singletonList(messageConverter));
//    }


}
