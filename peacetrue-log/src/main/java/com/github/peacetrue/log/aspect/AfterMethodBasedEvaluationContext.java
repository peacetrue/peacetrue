package com.github.peacetrue.log.aspect;

import lombok.Getter;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * @author xiayx
 */
@Getter
public class AfterMethodBasedEvaluationContext extends MethodBasedEvaluationContext {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final Object returning;

    AfterMethodBasedEvaluationContext(Object rootObject, Method method, Object[] arguments,
                                      ParameterNameDiscoverer parameterNameDiscoverer, Object returning) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
        this.target = rootObject;
        this.method = method;
        this.arguments = arguments;
        this.returning = returning;
    }
}
