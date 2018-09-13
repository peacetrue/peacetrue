package com.github.peacetrue.sign.append;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.jackson.JacksonUtils;
import com.github.peacetrue.sign.AppSecretCapable;
import com.github.peacetrue.sign.SignGenerator;
import com.github.peacetrue.sign.SignUtils;
import com.github.peacetrue.sign.SignedBean;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * 请求体签名服务
 *
 * @author xiayx
 * @see BodySignHttpMessageConverter
 * @see ParamSignService
 */
public class BodySignService implements SignService<Object, SignedBean<String>> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private AppSecretCapable appSecret;
    private SignGenerator signGenerator;
    private ObjectMapper objectMapper;

    @Override
    public SignedBean<String> sign(Object data, AppSecretCapable appSecret) {
        logger.info("sign the data: {}", data);
        if (appSecret == null) appSecret = this.appSecret;
        SignedBean<String> signed = new SignedBean<>();
        signed.setAppId(appSecret.getAppId());
        signed.setNonce(UUID.randomUUID().toString());
        signed.setTimestamp(System.currentTimeMillis());
        signed.setData(JacksonUtils.writeValueAsString(objectMapper, data));
        Map<String, Object> map = BeanUtils.map(signed);
        signed.setSign(signGenerator.generate(SignUtils.concat(map), appSecret.getAppSecret()));
        logger.debug("generate sign: {}", signed.getSign());
        return signed;
    }

    @Autowired
    public void setAppSecret(AppSecretCapable appSecret) {
        this.appSecret = appSecret;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setSignGenerator(SignGenerator signGenerator) {
        this.signGenerator = signGenerator;
    }
}
