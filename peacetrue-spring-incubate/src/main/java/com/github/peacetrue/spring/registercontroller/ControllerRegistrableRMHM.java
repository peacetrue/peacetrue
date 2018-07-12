package com.github.peacetrue.spring.registercontroller;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * used to register controller
 *
 * @author xiayx
 */
public class ControllerRegistrableRMHM extends RequestMappingHandlerMapping {

    /** @see #detectHandlerMethods(Object) */
    public void registerController(Object controller) {
        detectHandlerMethods(controller);
    }

    /** @see #detectHandlerMethods(Object) */
    public void registerController(Object controller, RequestMappingInfo requestMappingInfo) {
        Assert.notNull(requestMappingInfo, "requestMappingInfo must not be null");
//      //TODO spring 5
//        Class<?> handlerType = (controller instanceof String ?
//                obtainApplicationContext().getType((String) controller) : controller.getClass());
        Class<?> handlerType = null;
        if (handlerType != null) {
            final Class<?> userType = ClassUtils.getUserClass(handlerType);
            Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(userType,
                    (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method -> {
                        try {
                            return getMappingForMethod(method, userType);
                        } catch (Throwable ex) {
                            throw new IllegalStateException("Invalid mapping on handler class [" +
                                    userType.getName() + "]: " + method, ex);
                        }
                    });
            if (logger.isDebugEnabled()) {
                logger.debug(methods.size() + " request handler methods found on " + userType + ": " + methods);
            }
            for (Map.Entry<Method, RequestMappingInfo> entry : methods.entrySet()) {
                Method invocableMethod = AopUtils.selectInvocableMethod(entry.getKey(), userType);
                RequestMappingInfo mapping = entry.getValue();
                registerHandlerMethod(controller, invocableMethod, requestMappingInfo.combine(mapping));
            }
        }
    }
}
