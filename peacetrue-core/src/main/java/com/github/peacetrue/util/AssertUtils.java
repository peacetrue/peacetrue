package com.github.peacetrue.util;

/**
 * a util class for {@link org.junit.Assert}
 *
 * @author xiayx
 */
public abstract class AssertUtils {

    /**
     * assert there will be a exception in {@link ThrowableExecutor} with the given message.
     *
     * @param executor the test content need to execute
     * @param message  the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @return the throwable
     * @deprecated used {@link #assertException(String, ThrowableExecutor)} instead
     */
    public static Throwable assertException(ThrowableExecutor executor, String message) {
        return assertException(message, executor);
    }

    /**
     * assert there will be a exception in {@link ThrowableExecutor} with the given message.
     *
     * @param message  the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param executor the test content need to execute
     * @return the throwable
     */
    public static Throwable assertException(String message, ThrowableExecutor executor) {
        Throwable throwable = ThrowableUtils.getThrowable(executor);
        if (throwable != null) return throwable;

        if (message == null) throw new AssertionError();
        throw new AssertionError(message);
    }

    /**
     * assert there will be a exception in {@link ThrowableExecutor} with the given message.
     *
     * @param message  the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param executor the test content need to execute
     * @return the throwable
     */
    public static void assertNonException(String message, ThrowableExecutor executor) {
        Throwable throwable = ThrowableUtils.getThrowable(executor);
        if (throwable == null) return;

        if (message == null) throw new AssertionError();
        throw new AssertionError(message);
    }


    /**
     * assert there will be a exception in {@link ThrowableExecutor}
     *
     * @param executor the test content need to execute
     * @return the throwable
     */
    public static Throwable assertException(ThrowableExecutor executor) {
        return assertException(null, executor);
    }

}
