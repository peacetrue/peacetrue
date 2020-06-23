package com.github.peacetrue.operator.reactive;

import com.github.peacetrue.core.OperatorCapableImpl;
import com.github.peacetrue.operator.OperatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        log.info("设置方法[{}]的当前操作者", invocation.getMethod());
        Class<?> returnType = invocation.getMethod().getReturnType();
        OperatorCapableImpl<?> operator = (OperatorCapableImpl<?>) invocation.getArguments()[0];
        if (returnType.isAssignableFrom(Mono.class)) {
            return reactiveOperatorSupplier.getOperator()
                    .flatMap(defaultOperator -> {
                        log.debug("设置当前操作者:[{}]", defaultOperator);
                        OperatorUtils.setOperators(operator, defaultOperator);
                        try {
                            return (Mono<?>) invocation.proceed();
                        } catch (Throwable throwable) {
                            return Mono.error(throwable);
                        }
                    });

        } else if (returnType.isAssignableFrom(Flux.class)) {
            return reactiveOperatorSupplier.getOperator()
                    .flatMapMany(defaultOperator -> {
                        log.debug("设置当前操作者:[{}]", defaultOperator);
                        OperatorUtils.setOperators(operator, defaultOperator);
                        try {
                            return (Flux<?>) invocation.proceed();
                        } catch (Throwable throwable) {
                            return Flux.error(throwable);
                        }
                    });
        }
        return invocation.proceed();
    }
}