package com.github.peacetrue.util;

import org.junit.Assert;

/**
 * a util class for {@link org.junit.Assert}
 *
 * @author xiayx
 */
public abstract class AssertUtils {

    /**
     * no arg no return ,
     * differ from {@link java.util.function.Consumer}
     * or {@link java.util.function.Supplier}
     */
    public interface Executor {
        /**
         * Performs this operation
         *
         * @throws Throwable any exception
         */
        void execute() throws Throwable;
    }

    /**
     * assert there will be a exception in {@link Executor} with the given message.
     *
     * @param executor the test content need to execute
     * @param message  the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @return the throwable
     */
    public static Throwable assertException(Executor executor, String message) {
        try {
            executor.execute();
            Assert.fail();
        } catch (Throwable e) {
            return e;
        }
        Assert.fail(message);
        return null;
    }

    /**
     * assert there will be a exception in {@link Executor}
     *
     * @param executor the test content need to execute
     * @return the throwable
     */
    public static Throwable assertException(Executor executor) {
        return assertException(executor, null);
    }

}
