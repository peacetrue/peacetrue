package com.github.peacetrue.util;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * a util class for {@link java.util.stream.Collector}
 *
 * @author xiayx
 */
public abstract class CollectorUtils {

    /**
     * return last element in collection
     *
     * @param defaults the default value
     * @param <T>      the type of element
     * @return a {@code Collector} which implements the reduction operation
     */
    public static <T> Collector<T, ?, T> reducingToLast(T defaults) {
        return Collectors.reducing(defaults, StreamUtils.rightBinaryOperator());
    }

    /**
     * return last element in collection
     *
     * @param <T> the type of element
     * @return a {@code Collector} which implements the reduction operation
     */
    public static <T> Collector<T, ?, T> reducingToLast() {
        return reducingToLast(null);
    }


}
