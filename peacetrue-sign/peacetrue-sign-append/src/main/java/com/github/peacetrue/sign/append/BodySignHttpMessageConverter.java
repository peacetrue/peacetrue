package com.github.peacetrue.sign.append;

import com.github.peacetrue.sign.SignCapable;
import com.github.peacetrue.spring.http.converter.GenericHttpMessageConverterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 请求体消息转换器
 *
 * @author xiayx
 * @see BodySignService
 * @see ParamSignHttpMessageConverter
 */
public class BodySignHttpMessageConverter extends GenericHttpMessageConverterAdapter<Object> {

    private SignService<Object, ?> bodySignService;

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof SignCapable) {
            SignCapable signCapable = (SignCapable) o;
            o = bodySignService.sign(signCapable.getData(), signCapable);
        } else {
            o = bodySignService.sign(o);
        }
        super.write(o, type, contentType, outputMessage);
    }

    @Autowired
    public void setBodySignService(SignService<Object, ?> bodySignService) {
        this.bodySignService = bodySignService;
    }
}
