package com.github.peacetrue.operator.reactive;

import com.github.peacetrue.core.OperatorCapable;
import com.github.peacetrue.operator.OperatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author : xiayx
 * @since : 2020-06-23 03:21
 **/
@Slf4j
public class ReactiveOperatorMethodInterceptor implements MethodInterceptor {

    @Autowired
    private ReactiveOperatorSupplier reactiveOperatorSupplier;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> returnType = invocation.getMethod().getReturnType();
        boolean existsNullOperatorId = Arrays.stream(invocation.getArguments())
                .anyMatch(arg -> arg instanceof OperatorCapable && ((OperatorCapable<?>) arg).getOperatorId() == null);
        if (existsNullOperatorId) {
            if (returnType.isAssignableFrom(Mono.class)) {
                return reactiveOperatorSupplier.getOperator()
                        .flatMap(defaultOperator -> {
                            log.debug("设置方法[{}]当前操作者:[{}]", invocation.getMethod(), defaultOperator);
                            OperatorUtils.setOperators(invocation.getArguments(), defaultOperator);
                            try {
                                return (Mono<?>) invocation.proceed();
                            } catch (Throwable throwable) {
                                return Mono.error(throwable);
                            }
                        });
            } else if (returnType.isAssignableFrom(Flux.class)) {
                return reactiveOperatorSupplier.getOperator()
                        .flatMapMany(defaultOperator -> {
                            log.debug("设置方法[{}]当前操作者:[{}]", invocation.getMethod(), defaultOperator);
                            OperatorUtils.setOperators(invocation.getArguments(), defaultOperator);
                            try {
                                return (Flux<?>) invocation.proceed();
                            } catch (Throwable throwable) {
                                return Flux.error(throwable);
                            }
                        });
            }
        }
        return invocation.proceed();
    }
}
