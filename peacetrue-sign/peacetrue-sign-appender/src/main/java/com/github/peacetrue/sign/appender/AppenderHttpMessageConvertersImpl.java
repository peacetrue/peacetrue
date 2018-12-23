package com.github.peacetrue.sign.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;

/**
 * @author xiayx
 */
public class AppenderHttpMessageConvertersImpl implements AppenderHttpMessageConverters {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SignService<Object, ?> rawSignService;
    @Autowired
    private SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignService;
    @Autowired
    private ConversionService conversionService;

    @Override
    public HttpMessageConverter<Object> getRaw() {
        RawSignHttpMessageConverter messageConverter = new RawSignHttpMessageConverter();
        messageConverter.setHttpMessageConverter(new MappingJackson2HttpMessageConverter(objectMapper));
        messageConverter.setRawSignService(rawSignService);
        return messageConverter;
    }

    @Override
    public HttpMessageConverter<MultiValueMap<String, ?>> getForm() {
        FormSignHttpMessageConverter messageConverter = new FormSignHttpMessageConverter();
        FormHttpMessageConverter httpMessageConverter = new FormHttpMessageConverter();
        httpMessageConverter.addPartConverter(new ObjectToStringHttpMessageConverter(conversionService, StandardCharsets.UTF_8));
        messageConverter.setHttpMessageConverter(httpMessageConverter);
        messageConverter.setFormSignService(formSignService);
        return messageConverter;
    }
}
