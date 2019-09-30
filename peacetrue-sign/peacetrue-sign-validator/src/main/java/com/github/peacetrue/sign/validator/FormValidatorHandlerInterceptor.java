package com.github.peacetrue.sign.validator;

import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.sign.SignProperties;
import com.github.peacetrue.sign.SignValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * 处理请求参数签名验证
 *
 * @author xiayx
 * @see SignValid
 * @see RawValidatorRequestBodyAdvice
 */
public class FormValidatorHandlerInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private SignProperties signProperties;
    private SecretProvider secretProvider;
    private SignValidator<Map<String, String[]>> formValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //检查方法或者类上是否有 SignValid 注解，如果没有不处理
        if (!handlerMethod.hasMethodAnnotation(SignValid.class)
                && handlerMethod.getBeanType().getAnnotation(SignValid.class) == null) return true;

        //检查参数上是否有 RequestBody 注解，如果有不处理
        if (Arrays.stream(handlerMethod.getMethodParameters())
                .anyMatch(methodParameter -> methodParameter.hasParameterAnnotation(RequestBody.class))) return true;

        logger.debug("验证'{}'的签名", handler);
        String appId = request.getParameter(signProperties.getAppIdParamName());
        if (appId == null) throw new MissingServletRequestParameterException(signProperties.getAppIdParamName(), String.class.getSimpleName());
        logger.debug("取得应用标识: {}", appId);

        String sign = request.getParameter(signProperties.getSignParamName());
        if (sign == null) throw new MissingServletRequestParameterException(signProperties.getSignParamName(), String.class.getSimpleName());
        logger.debug("取得签名: {}", sign);

        String appSecret = secretProvider.getSecretById(appId);
        if (appSecret == null) throw new InvalidAppIdException(appId);
        logger.trace("取得应用秘钥: {}", appSecret);

        Map<String, String[]> parameters = request.getParameterMap();
        if (!formValidator.valid(parameters, appSecret)) throw new InvalidSignException(appId, sign);
        logger.trace("验证通过");

        return super.preHandle(request, response, handler);
    }

    @Autowired
    public void setSignProperties(SignProperties signProperties) {
        this.signProperties = signProperties;
    }

    @Autowired
    public void setSecretProvider(SecretProvider secretProvider) {
        this.secretProvider = secretProvider;
    }

    @Autowired
    public void setFormValidator(SignValidator<Map<String, String[]>> formValidator) {
        this.formValidator = formValidator;
    }
}
