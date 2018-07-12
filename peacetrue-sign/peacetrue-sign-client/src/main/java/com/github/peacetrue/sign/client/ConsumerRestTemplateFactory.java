package com.github.peacetrue.sign.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * a RestTemplateFactory for Consumer
 *
 * @author xiayx
 */
public class ConsumerRestTemplateFactory implements RestTemplateFactory {

    private ObjectMapper objectMapper;
    private HttpMessageConverter<Object> httpMessageConverter;

    @Override
    public RestTemplate getRestTemplate(String appId, String appSecret) {
        ConsumerClientSignService service = new ConsumerClientSignService();
        service.setAppId(appId);
        service.setAppSecret(appSecret);
        service.setObjectMapper(objectMapper);

        SignHttpMessageConverter messageConverter = new SignHttpMessageConverter();
        messageConverter.setClientSignService(service);
        messageConverter.setHttpMessageConverter(httpMessageConverter);

        return new RestTemplate(Collections.singletonList(messageConverter));
    }


    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public HttpMessageConverter<Object> getHttpMessageConverter() {
        return httpMessageConverter;
    }

    @Autowired
    @Qualifier("signHttpMessageConverter")
    public void setHttpMessageConverter(HttpMessageConverter<Object> httpMessageConverter) {
        this.httpMessageConverter = httpMessageConverter;
    }
}
