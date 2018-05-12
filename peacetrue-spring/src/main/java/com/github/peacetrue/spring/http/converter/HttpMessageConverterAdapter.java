package com.github.peacetrue.spring.http.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

/**
 * adapter for {@link HttpMessageConverter}
 *
 * @author xiayx
 */
public class HttpMessageConverterAdapter<T> implements HttpMessageConverter<T> {

    protected HttpMessageConverter<T> httpMessageConverter;

    protected HttpMessageConverterAdapter() {
    }

    public HttpMessageConverterAdapter(HttpMessageConverter<T> httpMessageConverter) {
        this.httpMessageConverter = httpMessageConverter;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return httpMessageConverter.canRead(clazz, mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return httpMessageConverter.canWrite(clazz, mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return httpMessageConverter.getSupportedMediaTypes();
    }

    @Override
    public T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return httpMessageConverter.read(clazz, inputMessage);
    }

    @Override
    public void write(T t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        httpMessageConverter.write(t, contentType, outputMessage);
    }

    public HttpMessageConverter<T> getHttpMessageConverter() {
        return httpMessageConverter;
    }

    public void setHttpMessageConverter(HttpMessageConverter<T> httpMessageConverter) {
        this.httpMessageConverter = httpMessageConverter;
    }
}
