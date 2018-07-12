package com.github.peacetrue.sign.server;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.sign.Signed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewRequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * check sign in {@link RequestBodyAdvice}
 *
 * @author xiayx
 */
public class SignCheckRequestBodyAdvice extends JsonViewRequestBodyAdvice {

    private ObjectMapper objectMapper;
    private SecretProvider secretProvider;
    private ServerSignService serverSignService;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getParameterAnnotation(SignData.class) != null
                || super.supports(methodParameter, targetType, converterType);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Class<?> deserializationView = null;
        JsonView jsonView = methodParameter.getParameterAnnotation(JsonView.class);
        if (jsonView != null) {
            Class<?>[] classes = jsonView.value();
            if (classes.length != 1) {
                throw new IllegalArgumentException(
                        "@JsonView only supported for request body advice with exactly 1 class argument: " + methodParameter);
            }
            deserializationView = classes[0];
        }

        InputStream inputStream = inputMessage.getBody();
        SignData signData = methodParameter.getParameterAnnotation(SignData.class);
        if (signData != null) {
            JavaType valueType = objectMapper.getTypeFactory().constructParametricType(Signed.class, String.class);
            Signed<String> signed = objectMapper.readValue(inputStream, valueType);
            String secret = secretProvider.getSecretById(signed.getAppId());
            if (secret == null) throw new InvalidAppIdException(signed.getAppId());
            if (!serverSignService.checkSign(signed, secret)) throw new InvalidSignException(signed);
            Object data = serverSignService.extractData(signed);
            inputStream = new ByteArrayInputStream(data == null ? new byte[0] : data.toString().getBytes(StandardCharsets.UTF_8));
        }

        return new MappingJacksonInputMessage(inputStream, inputMessage.getHeaders(), deserializationView);
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
    public void setServerSignService(ServerSignService serverSignService) {
        this.serverSignService = serverSignService;
    }
}
