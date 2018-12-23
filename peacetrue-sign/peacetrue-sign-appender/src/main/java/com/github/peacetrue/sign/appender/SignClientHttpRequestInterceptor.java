package com.github.peacetrue.sign.appender;

import com.github.peacetrue.spring.web.client.UriChangeClientHttpRequestInterceptor;
import com.github.peacetrue.util.URLDecoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * 用于为GET请求参数添加签名
 *
 * @author xiayx
 */
public class SignClientHttpRequestInterceptor extends UriChangeClientHttpRequestInterceptor {

    private SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (request.getMethod().equals(HttpMethod.GET)) request = changeUri(request);
        return execution.execute(request, body);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String getReplacedURIString(URI uri) {
        String decodeUri = URLDecoderUtils.decode(uri.toString());
        UriComponents components = UriComponentsBuilder.fromUriString(decodeUri).build();
        MultiValueMap<String, String> parameters = components.getQueryParams();
        parameters = (MultiValueMap) formSignService.sign(parameters);
        return UriComponentsBuilder.fromUri(uri).queryParams(parameters).build().encode().toString();
    }

    @Autowired
    public void setFormSignService(SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignService) {
        this.formSignService = formSignService;
    }
}
