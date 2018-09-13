package com.github.peacetrue.sign.valid;

import com.github.peacetrue.sign.SignUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求参数签名验证器
 *
 * @author xiayx
 * @see SignValidHandlerInterceptor#paramSignValidator
 * @see HttpServletRequest#getParameterMap()
 */
public class ParamSignValidator extends AbstractSignValidator<Map<String, String[]>> {

    @Override
    public boolean valid(Map<String, String[]> signedValue, String secret) {
        logger.debug("检查参数'{}'的签名", signedValue);
        Map<String, String[]> localSignedValue = new LinkedHashMap<>(signedValue);
        String clientSign = localSignedValue.remove(signProperties.getSignParamName())[0];
        logger.debug("取得客户端签名：{}", clientSign);
        String serverSign = signGenerator.generate(SignUtils.concat(localSignedValue), secret);
        logger.debug("生成服务单签名：{}", serverSign);
        return clientSign.equals(serverSign);

    }
}
