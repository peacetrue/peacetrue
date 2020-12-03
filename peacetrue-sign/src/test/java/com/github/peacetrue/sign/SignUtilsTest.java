package com.github.peacetrue.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiayx
 */
public class SignUtilsTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, String> params = new LinkedHashMap<>();
    private String appId = "demo";
    private String appSecret = "123456";

    {
        params.put("c", "3");
        params.put("b", "2");
        params.put("a", "1");
    }

    @Test
    public void generateSign() throws Exception {
        logger.info("original: {}", objectMapper.writeValueAsString(params));
        Map<String, String> sorted = SignUtils.sort(params);
        logger.info("sorted: {}", objectMapper.writeValueAsString(sorted));
        String string = SignUtils.concat(sorted);
        logger.info("concat: {}", string);
        String sign = SignUtils.generateSign(string, appSecret);
        logger.info("sign: {}", sign);
    }

    @Test
    public void sign() throws Exception {
        logger.info("original: {}", objectMapper.writeValueAsString(params));
        SignUtils.sign(params, appId, appSecret);
        logger.info("signed: {}", objectMapper.writeValueAsString(params));
    }

    @Test
    public void jsonSign() throws Exception {
        logger.info("original: {}", objectMapper.writeValueAsString(this.params));
        Map<String, String> params = new LinkedHashMap<>();
        params.put("data", objectMapper.writeValueAsString(this.params));
        logger.info("data: {}", objectMapper.writeValueAsString(params));
        SignUtils.sign(params, appId, appSecret);
        logger.info("signed: {}", objectMapper.writeValueAsString(params));
    }
}
