package com.github.peacetrue.util;

import java.util.Collection;
import java.util.function.*;

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


    /**
     * static quote for {@link Predicate}
     *
     * @param anything anything
     * @param <T>      any type
     * @return true
     */
    public static <T> boolean alwaysTrue(T anything) {
        return true;
    }

    /**
     * static quote for {@link Predicate}
     *
     * @param anything anything
     * @param <T>      any type
     * @return false
     */
    public static <T> boolean alwaysFalse(T anything) {
        return false;
    }

    /**
     * Returns a BinaryOperator that always returns its right argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its right argument
     */
    public static <T> BinaryOperator<T> rightBinaryOperator() {
        return (t, t2) -> t2;
    }


    /**
     * from Consumer to UnaryOperator, return the input arg
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its argument
     */
    public static <T> UnaryOperator<T> fromConsumer(Consumer<T> consumer) {
        return (t) -> {
            consumer.accept(t);
            return t;
        };
    }

    /** return the same value as first argument */
    public static <T, U> BiFunction<T, U, T> toBiFunction(BiConsumer<T, U> biConsumer) {
        return (t, u) -> {
            biConsumer.accept(t, u);
            return t;
        };
    }

    /** reduce to collection */
    public static <T extends Collection<U>, U> BiFunction<T, U, T> reduceToCollection() {
        return toBiFunction(Collection::add);
    }

}
