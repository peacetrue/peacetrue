package com.github.peacetrue.operator.reactive;

import com.github.peacetrue.core.IdCapable;
import com.github.peacetrue.core.OperatorCapable;
import com.github.peacetrue.core.OperatorCapableImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

/**
 * 切面自动配置
 *
 * @author : xiayx
 * @since : 2020-06-21 14:56
 **/
@ConditionalOnClass(name = "reactor.core.publisher.Mono")
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackageClasses = ReactiveOperatorAutoConfiguration.class)
public class ReactiveOperatorAutoConfiguration {

    @SuppressWarnings("unchecked")
    @Bean
    @ConditionalOnMissingBean(ReactiveOperatorSupplier.class)
    public ReactiveOperatorSupplier reactiveOperatorSupplier() {
        return () -> ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof IdCapable) {
                        OperatorCapableImpl<Long> operator = new OperatorCapableImpl<>();
                        operator.setOperatorId(((IdCapable<Long>) authentication.getPrincipal()).getId());
                        operator.setOperatorName(authentication.getName());
                        return operator;
                    }

                    OperatorCapableImpl<String> operator = new OperatorCapableImpl<>();
                    operator.setOperatorId(authentication.getName());
//                    operator.setOperatorName(authentication.getPrincipal().toString());
                    return operator;
                });
    }

    @Bean
    @ConditionalOnMissingBean(DefaultAdvisorAutoProxyCreator.class)
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public MethodInterceptor reactiveOperatorMethodInterceptor() {
        return new ReactiveOperatorMethodInterceptor();
    }

    @Bean
    public Pointcut reactiveOperatorPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.github.peacetrue..*Service.*(*,..))");
        pointcut.setParameterTypes(OperatorCapable.class);
        return pointcut;
    }

    @Bean
    public PointcutAdvisor reactiveOperatorPointcutAdvisor(
            Pointcut reactiveOperatorPointcut,
            MethodInterceptor reactiveOperatorMethodInterceptor) {
        return new DefaultPointcutAdvisor(reactiveOperatorPointcut, reactiveOperatorMethodInterceptor);
    }

//    @Bean
//    public UserService userService(
//            UserService userServiceImpl,
//            Pointcut reactiveOperatorPointcut,
//            MethodInterceptor reactiveOperatorMethodInterceptor) {
//        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//        proxyFactoryBean.setInterfaces(UserService.class);
//        proxyFactoryBean.setTarget(userServiceImpl);
//        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(reactiveOperatorPointcut, reactiveOperatorMethodInterceptor));
//        return (UserService) proxyFactoryBean.getObject();
//    }

}
