package com.github.peacetrue.util;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * a util class for {@link java.util.Collection}.
 *
 * @author xiayx
 */
public abstract class CollectionUtils {

    private static Map<Class, Object[]> EMPTY_ARRAY = new HashMap<>();

    /**
     * Returns an empty array (immutable).
     *
     * @param clazz the class of array type
     * @param <T>   the type of array
     * @return the empty array
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] emptyArray(Class<T> clazz) {
        if (!EMPTY_ARRAY.containsKey(clazz))
            EMPTY_ARRAY.put(clazz, (Object[]) Array.newInstance(clazz, 0));
        return (T[]) EMPTY_ARRAY.get(clazz);
    }


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

    /**
     * convert the element of array
     *
     * @param array     a array
     * @param converter a converter
     * @param <T>       the type of array
     * @return the array
     */
    public static <T> T[] map(T[] array, UnaryOperator<T> converter) {
        for (int i = 0; i < array.length; i++) {
            array[i] = converter.apply(array[i]);
        }
        return array;
    }

}
