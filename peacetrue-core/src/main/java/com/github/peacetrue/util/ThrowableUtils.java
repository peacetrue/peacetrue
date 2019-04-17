package com.github.peacetrue.util;

import javax.annotation.Nullable;

/**
 * @author xiayx
 */
public abstract class ThrowableUtils {

    /** 获取一段代码执行后抛出的异常 */
    @Nullable
    public static Throwable getThrowable(ThrowableExecutor executor) {
        try {
            executor.execute();
        } catch (Throwable throwable) {
            return throwable;
        }
        return null;
    }
}
