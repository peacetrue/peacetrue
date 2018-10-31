package com.github.peacetrue.sign.append;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.AppSecretCapable;
import com.github.peacetrue.sign.Md5SignGenerator;
import com.github.peacetrue.sign.SignGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * 发送请求时追加参数签名
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(SignAppendProperties.class)
public class SignAppendAutoConfiguration {

    private SignAppendProperties properties;

    public SignAppendAutoConfiguration(SignAppendProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean(SignGenerator.class)
    public SignGenerator signGenerator() {
        return new Md5SignGenerator();
    }

    @Bean
    public AppSecretCapable appSecret() {
        return properties.getClient();
    }

    /** 用于客户端向服务端发送请求 */
    @Bean
    @ConditionalOnMissingBean(name = "clientRestTemplate")
    public ClientRestTemplate clientRestTemplate(HttpMessageConverter<MultiValueMap<String, ?>> paramSignHttpMessageConverter,
                                                 HttpMessageConverter<Object> bodySignHttpMessageConverter,
                                                 ClientHttpRequestInterceptor signClientHttpRequestInterceptor) {
        ClientRestTemplate restTemplate = new ClientRestTemplate(properties.getClient().getUri(),
                Arrays.asList(paramSignHttpMessageConverter, bodySignHttpMessageConverter));
        restTemplate.getInterceptors().add(signClientHttpRequestInterceptor);
        return restTemplate;
    }

    /** 用于处理通过RequestBody传送的数据 */
    @Bean
    @ConditionalOnMissingBean(name = "bodySignHttpMessageConverter")
    public HttpMessageConverter<Object> bodySignHttpMessageConverter(ObjectMapper objectMapper) {
        BodySignHttpMessageConverter messageConverter = new BodySignHttpMessageConverter();
        messageConverter.setHttpMessageConverter(new MappingJackson2HttpMessageConverter(objectMapper));
        return messageConverter;
    }

    /** 用于处理通过RequestBody传送的数据 */
    @Bean
    @ConditionalOnMissingBean(name = "bodySignService")
    public SignService bodySignService() {
        return new BodySignService();
    }

    /** 用于处理post请求参数 */
    @Bean
    @ConditionalOnMissingBean(name = "paramSignHttpMessageConverter")
    public HttpMessageConverter<MultiValueMap<String, ?>> paramSignHttpMessageConverter() {
        ParamSignHttpMessageConverter messageConverter = new ParamSignHttpMessageConverter();
        messageConverter.setHttpMessageConverter(new FormHttpMessageConverter());
        return messageConverter;
    }

    /** 用于处理post请求参数 */
    @Bean
    @ConditionalOnMissingBean(name = "paramSignService")
    public SignService paramSignService() {
        return new ParamSignService();
    }

    /** 用于处理get请求参数 */
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
