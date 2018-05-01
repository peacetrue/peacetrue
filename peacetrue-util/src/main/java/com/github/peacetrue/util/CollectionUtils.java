package com.github.peacetrue.util;


/**
 * a util class for {@link java.util.Collection}.
 *
 * @author xiayx
 */
public abstract class CollectionUtils {

    /**
     * detect the type of iterable element,
     * use the type of first element.
     *
     * @param iterable iterable must not be empty
     * @param <T>      the type of iterable element
     * @return the class of iterable element
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> detectElementType(Iterable<T> iterable) {
        return (Class<? extends T>) iterable.iterator().next().getClass();
    }

    /**
     * detect the type of array element.
     * use the type of first element.
     *
     * @param array array must not be empty
     * @param <T>   the type of array element
     * @return the class of array element
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> detectElementType(T[] array) {
        return (Class<? extends T>) array[0].getClass();
    }

}
