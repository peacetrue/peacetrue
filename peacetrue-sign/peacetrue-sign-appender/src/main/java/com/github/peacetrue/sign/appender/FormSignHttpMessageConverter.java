package com.github.peacetrue.sign.appender;

import com.github.peacetrue.spring.http.converter.HttpMessageConverterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

/**
 * 参数签名消息转换器
 *
 * @author xiayx
 * @see RawSignHttpMessageConverter
 * @see FormSignService
 */
public class FormSignHttpMessageConverter extends HttpMessageConverterAdapter<MultiValueMap<String, ?>> {

    private SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignService;

    @Override
    public void write(MultiValueMap<String, ?> stringMultiValueMap, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.write(formSignService.sign(stringMultiValueMap), contentType, outputMessage);
    }

    @Autowired
    public void setFormSignService(SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignService) {
        this.formSignService = formSignService;
    }
}
