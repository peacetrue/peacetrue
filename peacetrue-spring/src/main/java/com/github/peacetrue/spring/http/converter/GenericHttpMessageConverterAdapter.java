package com.github.peacetrue.spring.http.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * adapter for {@link GenericHttpMessageConverter}
 *
 * @param <T> the type of message
 * @author xiayx
 */
public class GenericHttpMessageConverterAdapter<T> extends HttpMessageConverterAdapter<T> implements GenericHttpMessageConverter<T> {

    protected GenericHttpMessageConverterAdapter() {
    }

    public GenericHttpMessageConverterAdapter(GenericHttpMessageConverter<T> httpMessageConverter) {
        super(httpMessageConverter);
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return getHttpMessageConverter().canRead(type, contextClass, mediaType);
    }

    @Override
    public T read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return getHttpMessageConverter().read(type, contextClass, inputMessage);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return getHttpMessageConverter().canWrite(type, clazz, mediaType);
    }

    @Override
    public void write(T t, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        getHttpMessageConverter().write(t, type, contentType, outputMessage);
    }

    @Override
    public GenericHttpMessageConverter<T> getHttpMessageConverter() {
        return (GenericHttpMessageConverter<T>) super.getHttpMessageConverter();
    }

    public void setHttpMessageConverter(GenericHttpMessageConverter<T> httpMessageConverter) {
        super.setHttpMessageConverter(httpMessageConverter);
    }
}
