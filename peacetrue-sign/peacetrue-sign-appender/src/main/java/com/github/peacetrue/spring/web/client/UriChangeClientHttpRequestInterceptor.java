package com.github.peacetrue.spring.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 修改请求的链接
 *
 * @author xiayx
 */
public abstract class UriChangeClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(changeUri(request), body);
    }

    /** 修改请求的链接 */
    protected HttpRequest changeUri(HttpRequest request) {
        logger.debug("修改请求链接", request);
        if (!replaceHttpRequestURI(request))
            replaceURIString(request.getURI());
        return request;
    }

    /**
     * @see org.springframework.http.client.InterceptingClientHttpRequest#uri
     */
    protected boolean replaceHttpRequestURI(HttpRequest request) {
        logger.debug("修改 HttpRequest '{}' 的 uri '{}'", request, request.getURI());

        Field fieldUri = ReflectionUtils.findField(request.getClass(), "uri");
        if (fieldUri == null) return false;

        fieldUri.setAccessible(true);
        Object uri = ReflectionUtils.getField(fieldUri, request);
        if (!(uri instanceof URI)) return false;

        try {
            URI newUri = getUriOfHttpRequest(request);
            ReflectionUtils.setField(fieldUri, request, newUri);
            logger.debug("修改后的uri为: {}", newUri);
        } catch (URISyntaxException e) {
            logger.debug("设置请求链接异常", e);
            return false;
        }

        return true;
    }

    protected URI getUriOfHttpRequest(HttpRequest request) throws URISyntaxException {
        return new URI(getReplacedURIString(request.getURI()));
    }

    /**
     * @see URI#string
     * @see URI#toString()
     * @see URI#toASCIIString()
     */
    protected boolean replaceURIString(URI uri) {
        logger.debug("修改URI.string='{}'", uri);
        Field stringValue = ReflectionUtils.findField(uri.getClass(), "string");
        stringValue.setAccessible(true);
        String replacedStringValue = getReplacedURIString(uri);
        ReflectionUtils.setField(stringValue, uri, replacedStringValue);
        logger.debug("修改后的URI.string='{}'", replacedStringValue);
        return true;
    }

    protected abstract String getReplacedURIString(URI uri);

}
