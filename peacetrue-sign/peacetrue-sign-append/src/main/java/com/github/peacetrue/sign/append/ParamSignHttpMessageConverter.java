package com.github.peacetrue.sign.append;

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
 * @see BodySignHttpMessageConverter
 * @see ParamSignService
 */
public class ParamSignHttpMessageConverter extends HttpMessageConverterAdapter<MultiValueMap<String, ?>> {

    private SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> paramSignService;

    @Override
    public void write(MultiValueMap<String, ?> stringMultiValueMap, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.write(paramSignService.sign(stringMultiValueMap), contentType, outputMessage);
    }

    @Autowired
    public void setParamSignService(SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> paramSignService) {
        this.paramSignService = paramSignService;
    }
}
