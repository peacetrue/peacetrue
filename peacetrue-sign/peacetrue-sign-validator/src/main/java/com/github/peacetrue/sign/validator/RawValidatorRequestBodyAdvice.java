package com.github.peacetrue.sign.validator;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.sign.SignValid;
import com.github.peacetrue.sign.SignedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 处理请求体签名验证
 *
 * @author xiayx
 * @see SignValid
 * @see FormValidatorHandlerInterceptor
 */
@ControllerAdvice
public class RawValidatorRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper;
    private SecretProvider secretProvider;
    private SignValidator<Object> rawValidator;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(SignValid.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        logger.debug("验证@RequestBody参数的签名");
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(SignedData.class, String.class);
        SignedData<String> signedData = objectMapper.readValue(inputMessage.getBody(), valueType);
        logger.debug("取得签名的数据: {}", signedData);
        String secret = secretProvider.getSecretById(signedData.getAppId());
        logger.debug("取得应用密钥: {}", secret);
        if (secret == null) throw new InvalidAppIdException(signedData.getAppId());
        if (!rawValidator.valid(signedData, secret)) throw new InvalidSignException(signedData.getAppId(), signedData.getSign());
        Object data = signedData.getData();
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
    public void setRawValidator(SignValidator<Object> rawValidator) {
        this.rawValidator = rawValidator;
    }
}
