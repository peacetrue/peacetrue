package com.github.peacetrue.spring.util;

import com.github.peacetrue.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * a util class for {@link Enum}.
 * the enum can be considered to be a bean,
 * The difference is that enum has it's own properties
 * {@link Enum#ordinal()} and {@link Enum#name}
 *
 * @author xiayx
 * @see BeanUtils
 */
public abstract class EnumUtils {

    /** define the name of {@link Enum}' own property */
    public static final String
            PROPERTY_ORDINAL = Enum.class.getName() + ".ordinal",
            PROPERTY_NAME = Enum.class.getName() + ".name";

    /** define the method of {@link Enum}' own property */
    private static final Map<String, Method> INNER_METHODS = new HashMap<>();

    static {
        INNER_METHODS.put(PROPERTY_ORDINAL, ReflectionUtils.findMethod(Enum.class, "ordinal"));
        INNER_METHODS.put(PROPERTY_NAME, ReflectionUtils.findMethod(Enum.class, "name"));
    }

    /**
     * convert enum array to {@link Map},
     * each enum as a {@link Map.Entry},
     * the value of keyProperty as key of map,
     * the value of valueProperty as value of map.
     *
     * @param enums         enum array
     * @param keyProperty   a property in enum as key of map
     * @param valueProperty a property in enum as value of map
     * @param <E>           the type of enum
     * @param <K>           the type of keyProperty'value
     * @param <V>           the type of valueProperty'value
     * @return the converted map
     */
    public static <E extends Enum<E>, K, V> Map<K, V> map(E[] enums, String keyProperty, String valueProperty) {
        return _map(enums, keyProperty, valueProperty);
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> _map(Object[] beans, String keyProperty, String valueProperty) {
        if (beans.length == 0) return Collections.emptyMap();
        Class beanClass = CollectionUtils.detectElementType(beans);
        Method keyMethod = getReadMethod(beanClass, keyProperty);
        if (valueProperty == null) {
            return Arrays.stream(beans).collect(Collectors.toMap(
                    element -> (K) ReflectionUtils.invokeMethod(keyMethod, element),
                    element -> (V) element,
                    throwingMerger(), LinkedHashMap::new
            ));
        }

        Method valueMethod = getReadMethod(beanClass, valueProperty);
        return Arrays.stream(beans).collect(Collectors.toMap(
                element -> (K) ReflectionUtils.invokeMethod(keyMethod, element),
                element -> (V) ReflectionUtils.invokeMethod(valueMethod, element),
                throwingMerger(), LinkedHashMap::new
        ));
    }

    /** similar to {@link #_map(Object[], String, String)}, but support {@link Collection} */
    static <K, V> Map<K, V> _map(Collection<?> beans, String keyProperty, String valueProperty) {
        return _map(beans.toArray(new Object[beans.size()]), keyProperty, valueProperty);
    }

    private static Method getReadMethod(Class<?> beanClass, String property) {
        if (INNER_METHODS.containsKey(property)) return INNER_METHODS.get(property);

        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(beanClass, property);
        if (descriptor == null) BeanUtils.throwPropertyNotFoundException(beanClass, property);
        return descriptor.getReadMethod();
    }

    /** @see Collectors#throwingMerger() */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

//    /** similar to {@link #map(Enum[], String, String)}, got {@code enums} from {@code enumClass} */
//    public static <E extends Enum<E>, K, V> Map<K, V> map(Class<E> enumClass, String keyProperty, String valueProperty) {
//        return map(enumClass.getEnumConstants(), keyProperty, valueProperty);
//    }
//
//    /** similar to {@link #map(Enum[], String, String)}, set {@link #PROPERTY_NAME} as default {@code keyProperty} */
//    public static <E extends Enum<E>, V> Map<String, V> map(E[] enums, String valueProperty) {
//        return map(enums, PROPERTY_NAME, valueProperty);
//    }
//
//    /** similar to {@link #map(Enum[], String)}, got {@code enums} from {@code enumClass} */
//    public static <E extends Enum<E>, V> Map<String, V> map(Class<? extends E> beanClass, String valueProperty) {
//        return map(beanClass.getEnumConstants(), valueProperty);
//    }

}
