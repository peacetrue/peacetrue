package com.github.peacetrue.lock.aspect;

import com.github.peacetrue.lock.service.Lock;
import com.github.peacetrue.lock.service.LockService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

/**
 * @author xiayx
 */
@Aspect
public class LockAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LockService lockService;
    @Autowired
    private ExpressionParser expressionParser;

    /** 拦截含有{@link LockPoint}注解的方法 */
    @Around("@annotation(lockPoint)")
    public Object process(ProceedingJoinPoint joinPoint, LockPoint lockPoint) throws Throwable {
        logger.debug("锁住方法: {}", joinPoint.getSignature().toShortString());

        Expression expression = expressionParser.parseExpression(lockPoint.key(), ParserContext.TEMPLATE_EXPRESSION);
        SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().withRootObject(joinPoint.getArgs()).build();
        String key = expression.getValue(context, String.class);
        logger.debug("取得键值: {}", key);

        Lock lock = lockPoint.expiry() == -1
                ? lockService.create(key)
                : lockService.create(key, lockPoint.expiry());
        logger.debug("创建锁: {}", lock);

        try {
            return joinPoint.proceed();
        } finally {
            boolean release = lockService.release(lock.getName(), lock.getValue());
            logger.debug("释放锁: {}，并返回: {}", lock, release);
        }
    }
}
