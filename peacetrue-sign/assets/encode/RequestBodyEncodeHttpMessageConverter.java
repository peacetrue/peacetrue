package com.github.peacetrue.sign.encode;

import com.github.peacetrue.sign.SignCapable;
import com.github.peacetrue.spring.http.converter.GenericHttpMessageConverterAdapter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * encode data for {@link org.springframework.web.bind.annotation.RequestBody}
 *
 * @author xiayx
 */
public class RequestBodyEncodeHttpMessageConverter extends GenericHttpMessageConverterAdapter<Object> {

    private SignEncodeService<Object, ?> requestBodySignEncodeService;

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof SignCapable) {
            SignCapable signable = (SignCapable) o;
            o = requestBodySignEncodeService.sign(signable.getData(), signable);
        } else {
            o = requestBodySignEncodeService.sign(o);
        }
        super.write(o, type, contentType, outputMessage);
    }

    public void setRequestBodySignEncodeService(SignEncodeService<Object, ?> requestBodySignEncodeService) {
        this.requestBodySignEncodeService = requestBodySignEncodeService;
    }
}
