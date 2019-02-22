package com.github.peacetrue.log.aspect;

import com.github.peacetrue.log.service.AbstractLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * 日志构建器
 *
 * @author xiayx
 */
public interface LogBuilder {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Context {
        private Object bean;
        private Method method;
        private Object[] args;
        private Object returning;
    }

    AbstractLog build(Context context);
}
