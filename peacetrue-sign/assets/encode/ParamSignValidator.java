package com.github.peacetrue.sign.decode;

import com.github.peacetrue.sign.SignUtils;

import java.util.Map;

/**
 * 请求参数签名验证器
 *
 * @author xiayx
 * @see SignValidHandlerInterceptor#paramSignValidator
 */
public class ParamSignValidator extends AbstractSignValidator<Map<String, String[]>> {

    @Override
    public boolean valid(Map<String, String[]> signedValue, String secret) {
        logger.info("check sign of {}", signedValue);
        String clientSign = signedValue.remove(signProperty)[0];
        logger.debug("the client sign is {}", clientSign);
        String serverSign = signGenerator.generate(SignUtils.concat(signedValue), secret);
        logger.debug("use the params: {} and secret: {} generate sign: {}", signedValue, secret, serverSign);
        return clientSign.equals(serverSign);

    }
}
