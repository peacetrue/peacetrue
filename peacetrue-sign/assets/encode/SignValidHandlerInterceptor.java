package com.github.peacetrue.sign.decode;

import com.github.peacetrue.sign.SecretProvider;
import com.github.peacetrue.sign.SignValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Map;

/**
 * 签名验证拦截器
 *
 * @author xiayx
 * @see SignValid
 * @see SignValidRequestBodyAdvice
 */
public class SignValidHandlerInterceptor extends HandlerInterceptorAdapter {

    private SecretProvider secretProvider;
    private SignValidator<Map<String, String[]>> paramSignValidator;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) return true;
//
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        if (!handlerMethod.hasMethodAnnotation(SignValid.class)) return true;
//
//        String appId = request.getParameter("appId");
//        if (appId == null) throw new MissingServletRequestParameterException("appId", String.class.getSimpleName());
//
//        String secret = secretProvider.getSecretById(appId);
//        if (secret == null) throw new InvalidAppIdException(appId);
//
//        Map<String, String[]> parameters = request.getParameterMap();
//        if (!paramSignValidator.valid(parameters, secret)) throw new InvalidSignException(parameters);
//
//        return super.preHandle(request, response, handler);
//    }

    @Autowired
    public void setSecretProvider(SecretProvider secretProvider) {
        this.secretProvider = secretProvider;
    }

    @Autowired
    public void setParamSignValidator(SignValidator<Map<String, String[]>> paramSignValidator) {
        this.paramSignValidator = paramSignValidator;
    }
}
