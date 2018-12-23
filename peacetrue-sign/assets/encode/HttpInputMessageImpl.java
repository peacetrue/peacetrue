package com.github.peacetrue.sign.decode;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.util.StreamUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author xiayx
 */
public class HttpInputMessageImpl implements HttpInputMessage {

    private HttpHeaders headers;
    private InputStream body;

    public HttpInputMessageImpl(HttpHeaders headers, @Nullable InputStream body) {
        this.headers = Objects.requireNonNull(headers);
        this.body = body == null ? StreamUtils.emptyInput() : body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }
}
