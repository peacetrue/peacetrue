package com.github.peacetrue.sign.validator;

import com.github.peacetrue.sign.SignUtils;
import com.github.peacetrue.spring.util.BeanUtils;

import java.util.Map;

/**
 * 请求体签名验证器
 *
 * @author xiayx
 * @see RawValidatorRequestBodyAdvice#rawValidator
 */
public class RawValidator extends AbstractSignValidator<Object> {

    @Override
    @SuppressWarnings("unchecked")
    public boolean valid(Object signedData, String secret) {
        logger.info("check sign of {}", signedData);
        Map<String, ?> params = BeanUtils.map(signedData);
        Object clientSign = params.remove("sign");
        logger.debug("the client sign is {}", clientSign);
        String serverSign = signGenerator.generate(SignUtils.concat(params), secret);
        logger.debug("use the params: {} and secret: {} generate sign: {}", params, secret, serverSign);
        return clientSign.equals(serverSign);
    }

}
