package com.github.peacetrue.log.aspect;

import org.aspectj.lang.JoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author xiayx
 */
public class AsyncLogAspect extends LogAspect {

    @Async("logTaskExecutor")
    public void process(JoinPoint joinPoint, LogPoint lockPoint, Object returning) throws Throwable {
        super.process(joinPoint, lockPoint, returning);
    }
}
