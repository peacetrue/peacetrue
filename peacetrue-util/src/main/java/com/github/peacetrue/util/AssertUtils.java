package com.github.peacetrue.util;

import org.junit.Assert;

/**
 * a util class for {@link org.junit.Assert}
 *
 * @author xiayx
 */
public abstract class AssertUtils {

    /** no arg no return differ from {@link java.util.function.Consumer} or {@link java.util.function.Supplier} */
    public interface Executor {
        /** Performs this operation */
        void execute();
    }

    /** assert there will be a exception in {@link Executor} with the given message. */
    public static void assertException(Executor executor, String message) {
        try {
            executor.execute();
        } catch (Throwable e) {
            return;
        }
        Assert.fail(message);
    }

    /** assert there will be a exception in {@link Executor} */
    public static void assertException(Executor executor) {
        assertException(executor, null);
    }

}
