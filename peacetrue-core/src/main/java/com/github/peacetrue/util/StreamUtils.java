package com.github.peacetrue.util;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * a util class for {@link java.util.stream.Stream}
 *
 * @author xiayx
 */
public abstract class StreamUtils {
    /**
     * convert {@link Consumer} to {@link BiConsumer}
     *
     * @param consumer the consumer
     * @param <T>      the type of the first argument to the operation
     * @param <U>      the type of the second argument to the operation
     * @return the {@link BiConsumer}
     */
    public static <T, U> BiConsumer<T, U> toBiConsumer(Consumer<T> consumer) {
        return (t, o) -> consumer.accept(t);
    }

    /**
     * convert {@link Predicate} to {@link BiPredicate}
     *
     * @param predicate the predicate
     * @param <T>       the type of the first argument to the operation
     * @param <U>       the type of the second argument to the operation
     * @return the {@link BiPredicate}
     */
    public static <T, U> BiPredicate<T, U> toBiPredicate(Predicate<T> predicate) {
        return (t, o) -> predicate.test(t);
    }


    /** static quote for {@link Predicate} */
    public static <T> boolean alwaysTrue(T anything) {
        return true;
    }

    /** static quote for {@link Predicate} */
    public static <T> boolean alwaysFlase(T anything) {
        return false;
    }

}
