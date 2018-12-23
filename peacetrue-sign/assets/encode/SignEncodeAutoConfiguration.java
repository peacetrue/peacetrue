package com.github.peacetrue.sign.encode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.CachedSecretProvider;
import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.spring.web.client.UriRestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * the AutoConfiguration for client sign
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(SignEncodeProperties.class)
public class SignEncodeAutoConfiguration {

    private SignEncodeProperties properties;

    public SignEncodeAutoConfiguration(SignEncodeProperties properties) {
        this.properties = properties;
    }

    /** 用于客户端向服务端发送请求 */
    @Bean
    @ConditionalOnMissingBean(name = "clientRestTemplate")
    public UriRestTemplate clientRestTemplate(HttpMessageConverter<MultiValueMap<String, ?>> encodeFormHttpMessageConverter,
                                              HttpMessageConverter<Object> encodeRequestBodyHttpMessageConverter) {
        return new UriRestTemplate(properties.getClient().getUri(),
                Arrays.asList(encodeFormHttpMessageConverter, encodeRequestBodyHttpMessageConverter));
    }

    /** 用于处理通过RequestBody传送的数据 */
    @Bean
    @ConditionalOnMissingBean(name = "requestBodyEncodeHttpMessageConverter")
    public HttpMessageConverter<Object> requestBodyEncodeHttpMessageConverter(ObjectMapper objectMapper) {
        RequestBodyEncodeHttpMessageConverter messageConverter = new RequestBodyEncodeHttpMessageConverter();
        messageConverter.setHttpMessageConverter(new MappingJackson2HttpMessageConverter(objectMapper));
        return messageConverter;
    }

//    /** 用于处理通过RequestBody传送的数据 */
//    @Bean
//    @ConditionalOnMissingBean(name = "requestBodySignEncodeService")
//    public SignEncodeService requestBodySignEncodeService() {
//        return new RequestBodySignEncodeService();
//    }

    /** 用于处理通过form传送的数据 */
    @Bean
    @ConditionalOnMissingBean(name = "formEncodeHttpMessageConverter")
    public HttpMessageConverter<MultiValueMap<String, ?>> formEncodeHttpMessageConverter(ObjectMapper objectMapper) {
        return new FormHttpMessageConverter();
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
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

    @Bean
    @ConditionalOnMissingBean(SecretProvider.class)
    public SecretProvider secretProvider() {
        return new CachedSecretProvider(properties.getServer());
    }
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
