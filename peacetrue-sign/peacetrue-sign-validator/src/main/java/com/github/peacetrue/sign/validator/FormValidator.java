package com.github.peacetrue.sign.validator;

import com.github.peacetrue.sign.SignUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求参数签名验证器
 *
 * @author xiayx
 * @see FormValidatorHandlerInterceptor#formValidator
 * @see HttpServletRequest#getParameterMap()
 */
public class FormValidator extends AbstractSignValidator<Map<String, String[]>> {

    @Override
    public boolean valid(Map<String, String[]> signedData, String secret) {
        logger.debug("检查参数'{}'的签名", signedData);
        Map<String, String[]> localSignedValue = new LinkedHashMap<>(signedData);
        String clientSign = localSignedValue.remove(signProperties.getSignParamName())[0];
        localSignedValue.remove(signProperties.getAppSecretParamName());
        logger.debug("取得客户端签名：{}", clientSign);
        String serverSign = signGenerator.generate(SignUtils.concat(SignUtils.toString(localSignedValue)), secret);
        logger.debug("生成服务端签名：{}", serverSign);
        return clientSign.equalsIgnoreCase(serverSign);

    }
}
