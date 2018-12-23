package com.github.peacetrue.sign.appender;

import com.github.peacetrue.sign.Signable;
import com.github.peacetrue.spring.http.converter.GenericHttpMessageConverterAdapter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 原始参数消息转换器
 *
 * @author xiayx
 * @see RawSignService
 * @see FormSignHttpMessageConverter
 */
public class RawSignHttpMessageConverter extends GenericHttpMessageConverterAdapter<Object> {

    private SignService<Object, ?> rawSignService;

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof Signable) {
            Signable signCapable = (Signable) o;
            o = rawSignService.sign(signCapable.getData(), signCapable);
        } else {
            o = rawSignService.sign(o);
        }
        super.write(o, type, contentType, outputMessage);
    }

    public void setRawSignService(SignService<Object, ?> rawSignService) {
        this.rawSignService = rawSignService;
    }
}
