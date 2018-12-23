package com.github.peacetrue.sign.decode;

import com.github.peacetrue.sign.SignUtils;
import com.github.peacetrue.spring.util.BeanUtils;

import java.util.Map;

/**
 * 请求体签名验证器
 *
 * @author xiayx
 * @see SignValidRequestBodyAdvice#bodySignValidator
 */
public class BodySignValidator extends AbstractSignValidator<Object> {

    @Override
    @SuppressWarnings("unchecked")
    public boolean valid(Object signedValue, String secret) {
        logger.info("check sign of {}", signedValue);
        Map<String, ?> params = BeanUtils.map(signedValue);
        Object clientSign = params.remove(signProperty);
        logger.debug("the client sign is {}", clientSign);
        String serverSign = signGenerator.generate(SignUtils.concat(params), secret);
        logger.debug("use the params: {} and secret: {} generate sign: {}", params, secret, serverSign);
        return clientSign.equals(serverSign);
    }

}
