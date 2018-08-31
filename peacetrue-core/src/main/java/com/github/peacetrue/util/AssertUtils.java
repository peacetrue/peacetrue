package com.github.peacetrue.util;

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
        } catch (Throwable e) {
            return e;
        }
        if (message == null) throw new AssertionError();
        throw new AssertionError(message);
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
