package com.github.peacetrue.log.aspect;

import java.lang.annotation.*;

/**
 * 日志记录点，标注于方法上，表示该方法需要记录操作日志
 *
 * @author xiayx
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
//tag::class[]
public @interface LogPoint {

    /** 默认的日志构建器名称 */
    String DEFAULT_LOG_BUILDER = "logBuilder";

    /** 日志构建器的 bean name */
    String logBuilder() default DEFAULT_LOG_BUILDER;
}
//end::class[]