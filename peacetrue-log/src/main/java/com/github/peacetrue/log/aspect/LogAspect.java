package com.github.peacetrue.log.aspect;

import com.github.peacetrue.log.service.AbstractLog;
import com.github.peacetrue.log.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Method;

/**
 * 日志切面。拦截注解{@link LogPoint}标注的方法
 *
 * @author xiayx
 */
@Aspect
public class LogAspect implements BeanFactoryAware {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private BeanFactory beanFactory;
    private BeanFactoryResolver beanFactoryResolver;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.beanFactoryResolver = new BeanFactoryResolver(beanFactory);
    }

    /** 拦截含有{@link LogPoint}注解的方法 */
    @SuppressWarnings("unchecked")
    @AfterReturning(pointcut = "@annotation(lockPoint)", returning = "returning")
    public void process(JoinPoint joinPoint, LogPoint lockPoint, Object returning) throws Throwable {
        try {
            logger.debug("拦截方法: {}", joinPoint.getSignature().toShortString());
            LogBuilder logBuilder = beanFactory.getBean(lockPoint.logBuilder(), LogBuilder.class);
            logger.trace("取得日志构建器: {}", logBuilder);

            AfterMethodBasedEvaluationContext evaluationContext = new AfterMethodBasedEvaluationContext(
                    joinPoint.getTarget(), getMethod(joinPoint), joinPoint.getArgs(),
                    new AspectJAdviceParameterNameDiscoverer(joinPoint.getStaticPart().toLongString()),
                    returning
            );
            evaluationContext.setVariable("returning", returning);
            evaluationContext.setBeanResolver(this.beanFactoryResolver);
            logger.trace("创建日志上下文: {}", evaluationContext);

            AbstractLog log = logBuilder.build(evaluationContext);
            logger.trace("构建出日志: {}", log);
            beanFactory.getBean(LogService.class).add(log);
        } catch (Throwable e) {
            logger.error("添加日志异常", e);
        }
    }

    private static Method getMethod(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (!method.getDeclaringClass().isInterface()) return method;

        try {
            return joinPoint.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
}
