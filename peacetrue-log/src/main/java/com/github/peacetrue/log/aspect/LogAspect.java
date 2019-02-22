package com.github.peacetrue.log.aspect;

import com.github.peacetrue.log.service.AbstractLog;
import com.github.peacetrue.log.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * 日志切面。拦截注解{@link LogPoint}标注的方法
 *
 * @author xiayx
 */
@Aspect
public class LogAspect implements BeanFactoryAware {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LogService logService;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /** 拦截含有{@link LogPoint}注解的方法 */
    @SuppressWarnings("unchecked")
    @AfterReturning(pointcut = "@annotation(lockPoint)", returning = "returning")
    public void process(JoinPoint joinPoint, LogPoint lockPoint, Object returning) throws Throwable {
        logger.debug("拦截方法: {}", joinPoint.getSignature().toShortString());

        LogBuilder logBuilder = beanFactory.getBean(lockPoint.logBuilder(), LogBuilder.class);
        logger.trace("取得日志构建器: {}", logBuilder);

        LogBuilder.Context context = new LogBuilder.Context(
                joinPoint.getTarget(), getMethod(joinPoint),
                joinPoint.getArgs(), returning);
        logger.trace("创建日志上下文: {}", context);

        AbstractLog log = logBuilder.build(context);
        logger.trace("取得构建的日志: {}", log);

        try {
            logService.add(log);
        } catch (Exception e) {
            logger.error("添加日志异常", e);
        }
    }

    private static Method getMethod(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (!method.getDeclaringClass().isInterface()) return method;

        try {
            //TODO 未考虑父类方法
            return joinPoint.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
}
