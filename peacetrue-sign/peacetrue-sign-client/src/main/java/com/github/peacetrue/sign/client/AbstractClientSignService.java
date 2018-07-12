package com.github.peacetrue.sign.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.jackson.JacksonUtils;
import com.github.peacetrue.sign.SignUtils;
import com.github.peacetrue.sign.Signed;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * sign data with json
 *
 * @author xiayx
 */
public abstract class AbstractClientSignService implements ClientSignService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected ObjectMapper objectMapper;

    @Override
    public Object sign(Object data, String appId, String appSecret) {
        logger.info("sign the data");
        Signed<String> signed = new Signed<>();
        signed.setAppId(appId);
        signed.setNonce(UUID.randomUUID().toString());
        signed.setTimestamp(System.currentTimeMillis());
        signed.setData(JacksonUtils.writeValueAsString(objectMapper, data));
        Map<String, Object> map = BeanUtils.map(signed);
        signed.setSign(SignUtils.generateSign(map, appSecret));
        logger.debug("use the data: {} and secret: {} generate sign: {}", map, appSecret, signed.getSign());
        return signed;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
