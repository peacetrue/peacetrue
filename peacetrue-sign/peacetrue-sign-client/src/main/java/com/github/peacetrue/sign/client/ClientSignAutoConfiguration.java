package com.github.peacetrue.sign.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.CachedSecretProvider;
import com.github.peacetrue.sign.SecretProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * the AutoConfiguration for client sign
 *
 * @author xiayx
 */
@EnableConfigurationProperties(ClientSignProperties.class)
public class ClientSignAutoConfiguration {

    @Autowired
    private ClientSignProperties properties;

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean(name = "signHttpMessageConverter")
    public HttpMessageConverter<Object> signHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper());
    }

    @Bean
    @ConditionalOnMissingBean(name = "consumerRestTemplateFactory")
    public ConsumerRestTemplateFactory consumerRestTemplateFactory() {
        return new ConsumerRestTemplateFactory();
    }

    @Configuration
    public static class A {
        public A() {
        }

        @Autowired
        private ConsumerRestTemplateFactory consumerRestTemplateFactory;
        @Autowired
        private ClientSignProperties properties;

        @Bean
        @Autowired
        public BeanFactoryPostProcessor beanFactoryPostProcessor(ClientSignProperties properties,
                                                                 ConsumerRestTemplateFactory consumerRestTemplateFactory) {
            return beanFactory -> properties.getServiceApps().forEach((s, appBean) ->
                    beanFactory.registerSingleton(s + "ConsumerRestTemplate",
                            consumerRestTemplateFactory.getRestTemplate(appBean.getAppId(), appBean.getAppSecret())
                    ));
        }
    }

//    @Bean
//    @ConditionalOnMissingBean(name = "consumerRestTemplate")
//    public RestTemplate consumerRestTemplate(ConsumerRestTemplateFactory consumerRestTemplateFactory) {
//        AppBean app = properties.getConsumerApps().get(ClientSignProperties.DEFAULT_APP_NAME);
//        if (app == null) throw new IllegalStateException("you must config a app with appId and appSecret");
//        return consumerRestTemplateFactory.getRestTemplate(app.getAppId(), app.getAppSecret());
//    }

    @Bean
    @ConditionalOnMissingBean(SecretProvider.class)
    public SecretProvider secretProvider() {
        return new CachedSecretProvider(properties.getConsumerApps());
    }

    @Bean
    @ConditionalOnMissingBean(ProviderClientSignService.class)
    public ProviderClientSignService providerClientSignService() {
        return new ProviderClientSignService();
    }

    @Bean
    @ConditionalOnMissingBean(name = "providerRestTemplate")
    public RestTemplate providerRestTemplate() {
        SignHttpMessageConverter messageConverter = new SignHttpMessageConverter();
        messageConverter.setClientSignService(providerClientSignService());
        messageConverter.setHttpMessageConverter(signHttpMessageConverter());
        return new RestTemplate(Collections.singletonList(messageConverter));
    }


}
