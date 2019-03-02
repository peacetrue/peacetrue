package com.github.peacetrue.log.aspect;

import com.github.peacetrue.log.service.AbstractLog;

/**
 * 日志构建器
 *
 * @author xiayx
 */
public interface LogBuilder {
    /** 构建日志 */
    AbstractLog build(AfterMethodBasedEvaluationContext context);
}
