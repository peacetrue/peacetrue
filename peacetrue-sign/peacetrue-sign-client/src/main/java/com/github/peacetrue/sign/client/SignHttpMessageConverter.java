package com.github.peacetrue.sign.client;

import com.github.peacetrue.sign.Signable;
import com.github.peacetrue.spring.http.converter.HttpMessageConverterAdapter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

/**
 * {@link HttpMessageConverter} for sign
 *
 * @author xiayx
 */
public class SignHttpMessageConverter extends HttpMessageConverterAdapter<Object> {

    private ClientSignService clientSignService;

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return super.read(clazz, inputMessage);
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof Signable) {
            Signable signable = (Signable) o;
            o = clientSignService.sign(signable.getData(), signable.getAppId(), signable.getAppSecret());
        }
        super.write(o, contentType, outputMessage);
    }


    public void setClientSignService(ClientSignService clientSignService) {
        this.clientSignService = clientSignService;
    }
}
