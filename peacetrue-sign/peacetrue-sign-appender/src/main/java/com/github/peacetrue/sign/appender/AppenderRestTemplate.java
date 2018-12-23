package com.github.peacetrue.sign.appender;

import com.github.peacetrue.spring.web.client.UriRestTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * 基于{@link RestTemplate}，提供无uri参数的方法
 *
 * @author xiayx
 * @see RestTemplate
 */
public class AppenderRestTemplate extends UriRestTemplate {

    private String uri;

    public AppenderRestTemplate(String uri) {
        this.uri = Objects.requireNonNull(uri);
    }

    public AppenderRestTemplate(String uri, ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        this.uri = Objects.requireNonNull(uri);
    }

    public AppenderRestTemplate(String uri, List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        this.uri = Objects.requireNonNull(uri);
    }

    @Override
    protected String getUri() {
        return uri;
    }

}
