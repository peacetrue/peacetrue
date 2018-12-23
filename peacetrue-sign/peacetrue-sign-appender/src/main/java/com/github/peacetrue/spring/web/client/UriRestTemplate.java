package com.github.peacetrue.spring.web.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基于{@link RestTemplate}，提供无uri参数的方法
 *
 * @author xiayx
 * @see RestTemplate
 */
public abstract class UriRestTemplate extends RestTemplate {

    public UriRestTemplate() {
    }

    public UriRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public UriRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    protected abstract String getUri();

    public <T> T getForObject(Class<T> responseType, Object... urlVariables) throws RestClientException {
        return super.getForObject(getUri(), responseType, urlVariables);
    }

    public <T> T getForObject(Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        return super.getForObject(getUri(), responseType, urlVariables);
    }

    public <T> T getForObject(Class<T> responseType) throws RestClientException {
        return super.getForObject(getUri(), responseType);
    }

    public <T> ResponseEntity<T> getForEntity(Class<T> responseType, Object... urlVariables) throws RestClientException {
        return super.getForEntity(getUri(), responseType, urlVariables);
    }

    public <T> ResponseEntity<T> getForEntity(Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        return super.getForEntity(getUri(), responseType, urlVariables);
    }

    public <T> ResponseEntity<T> getForEntity(Class<T> responseType) throws RestClientException {
        return super.getForEntity(getUri(), responseType);
    }

    public HttpHeaders headForHeaders(Object... urlVariables) throws RestClientException {
        return super.headForHeaders(getUri(), urlVariables);
    }

    public HttpHeaders headForHeaders(Map<String, ?> urlVariables) throws RestClientException {
        return super.headForHeaders(getUri(), urlVariables);
    }

    public URI postForLocation(Object request, Object... urlVariables) throws RestClientException {
        return super.postForLocation(getUri(), request, urlVariables);
    }

    public URI postForLocation(Object request, Map<String, ?> urlVariables) throws RestClientException {
        return super.postForLocation(getUri(), request, urlVariables);
    }

    public URI postForLocation(Object request) throws RestClientException {
        return super.postForLocation(getUri(), request);
    }

    public <T> T postForObject(Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.postForObject(getUri(), request, responseType, uriVariables);
    }

    public <T> T postForObject(Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.postForObject(getUri(), request, responseType, uriVariables);
    }

    public <T> T postForObject(Object request, Class<T> responseType) throws RestClientException {
        return super.postForObject(getUri(), request, responseType);
    }

    public <T> ResponseEntity<T> postForEntity(Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.postForEntity(getUri(), request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.postForEntity(getUri(), request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(Object request, Class<T> responseType) throws RestClientException {
        return super.postForEntity(getUri(), request, responseType);
    }

    public void put(Object request, Object... urlVariables) throws RestClientException {
        super.put(getUri(), request, urlVariables);
    }

    public void put(Object request, Map<String, ?> urlVariables) throws RestClientException {
        super.put(getUri(), request, urlVariables);
    }

    public void put(Object request) throws RestClientException {
        super.put(getUri(), request);
    }

    public void delete(Object... urlVariables) throws RestClientException {
        super.delete(getUri(), urlVariables);
    }

    public void delete(Map<String, ?> urlVariables) throws RestClientException {
        super.delete(getUri(), urlVariables);
    }

    public Set<HttpMethod> optionsForAllow(Object... urlVariables) throws RestClientException {
        return super.optionsForAllow(getUri(), urlVariables);
    }

    public Set<HttpMethod> optionsForAllow(Map<String, ?> urlVariables) throws RestClientException {
        return super.optionsForAllow(getUri(), urlVariables);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.exchange(getUri(), method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.exchange(getUri(), method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        return super.exchange(getUri(), method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
        return super.exchange(getUri(), method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.exchange(getUri(), method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return super.exchange(getUri(), method, requestEntity, responseType);
    }

    public <T> T execute(HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Object... urlVariables) throws RestClientException {
        return super.execute(getUri(), method, requestCallback, responseExtractor, urlVariables);
    }

    public <T> T execute(HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables) throws RestClientException {
        return super.execute(getUri(), method, requestCallback, responseExtractor, urlVariables);
    }

    public <T> T execute(HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        return super.execute(getUri(), method, requestCallback, responseExtractor);
    }

    @Override
    protected <T> T doExecute(URI uri, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        String thisUri = getUri();
        String methodUri = uri.toString();
        if (!methodUri.equals(thisUri)) {
            String fullUri = thisUri + methodUri;
            try {
                uri = new URI(fullUri);
            } catch (URISyntaxException ignored) {
                throw new IllegalArgumentException("the uri '" + fullUri + "' is invalid", ignored);
            }
        }
        return super.doExecute(uri, method, requestCallback, responseExtractor);
    }
}
