package com.github.peacetrue.sign.decode;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.sign.SignValid;
import com.github.peacetrue.sign.SignedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * check sign in {@link RequestBodyAdvice}
 *
 * @author xiayx
 * @see SignValid
 */
@ControllerAdvice
public class SignValidRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private ObjectMapper objectMapper;
    private SecretProvider secretProvider;
    private SignValidator<Object> bodySignValidator;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(SignValid.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(SignedData.class, String.class);
        SignedData<String> signed = objectMapper.readValue(inputMessage.getBody(), valueType);
        String secret = secretProvider.getSecretById(signed.getAppId());
//        if (secret == null) throw new InvalidAppIdException(signed.getAppId());
        if (!bodySignValidator.valid(signed, secret)) throw new InvalidSignException(signed);
        Object data = signed.getData();
        InputStream body = data == null ? null : new ByteArrayInputStream(data.toString().getBytes(StandardCharsets.UTF_8));
        return new HttpInputMessageImpl(inputMessage.getHeaders(), body);
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setSecretProvider(SecretProvider secretProvider) {
        this.secretProvider = secretProvider;
    }

    @Autowired
    public void setBodySignValidator(SignValidator<Object> bodySignValidator) {
        this.bodySignValidator = bodySignValidator;
    }
}
