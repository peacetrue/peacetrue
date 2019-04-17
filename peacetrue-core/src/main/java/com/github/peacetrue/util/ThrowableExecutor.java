package com.github.peacetrue.util;

/**
 * no arg no return ,
 * differ from {@link java.util.function.Consumer}
 * or {@link java.util.function.Supplier}
 */
public interface ThrowableExecutor {
    /**
     * Performs this operation
     *
     * @throws Throwable any exception
     */
    void execute() throws Throwable;
}
