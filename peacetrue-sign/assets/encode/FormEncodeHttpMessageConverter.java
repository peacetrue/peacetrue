package com.github.peacetrue.sign.encode;

import com.github.peacetrue.spring.http.converter.HttpMessageConverterAdapter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

/**
 * 用于处理form参数
 *
 * @author xiayx
 */
public class FormEncodeHttpMessageConverter extends HttpMessageConverterAdapter<MultiValueMap<String, ?>> {

    private SignEncodeService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignEncodeService;

    @Override
    public void write(MultiValueMap<String, ?> stringMultiValueMap, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.write(stringMultiValueMap, contentType, outputMessage);
    }

    public void setFormSignEncodeService(SignEncodeService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> formSignEncodeService) {
        this.formSignEncodeService = formSignEncodeService;
    }
}
