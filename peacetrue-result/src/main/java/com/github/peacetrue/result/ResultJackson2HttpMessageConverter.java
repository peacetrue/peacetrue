package com.github.peacetrue.result;

import com.github.peacetrue.spring.http.converter.GenericHttpMessageConverterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * output anything as {@link Result} exclude {@link Result} self.
 *
 * @author xiayx
 */
public class ResultJackson2HttpMessageConverter extends GenericHttpMessageConverterAdapter<Object> {

    private ResultBuilder resultBuilder;

    public ResultJackson2HttpMessageConverter() {
    }

    public ResultJackson2HttpMessageConverter(GenericHttpMessageConverter<Object> httpMessageConverter) {
        super(httpMessageConverter);
    }

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (!(o instanceof Result)) o = resultBuilder.build(o);
        super.write(o, type, contentType, outputMessage);
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }

    @Override
    public GenericHttpMessageConverter<Object> getHttpMessageConverter() {
        return super.getHttpMessageConverter();
    }

    @Override
    public void setHttpMessageConverter(GenericHttpMessageConverter<Object> httpMessageConverter) {
        super.setHttpMessageConverter(httpMessageConverter);
    }
}
