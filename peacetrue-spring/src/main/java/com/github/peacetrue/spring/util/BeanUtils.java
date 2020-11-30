package com.github.peacetrue.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.*;

import javax.annotation.Nullable;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * extends for {@link BeanUtils}
 *
 * @author xiayx
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    private static final Method GET_CLASS_METHOD = ReflectionUtils.findMethod(Object.class, "getClass");

    /** a common judgment that an property value is blank */
    public static final BiPredicate<String, Object> EMPTY_PROPERTY_VALUE = (key, value) -> value == null
            || (value instanceof String && StringUtils.isEmpty(value))
            || (value instanceof Collection && CollectionUtils.isEmpty((Collection) value)
            || (value instanceof Object[] && ObjectUtils.isEmpty((Object[]) value)));

    /** a common judgment that an property value is not blank */
    public static final BiPredicate<String, Object> NOT_EMPTY_PROPERTY_VALUE = EMPTY_PROPERTY_VALUE.negate();

    /**
     * similar to {@link org.springframework.beans.BeanUtils#getPropertyDescriptors(Class)},
     * but exclude {@link Object#getClass()}
     *
     * @param clazz the Class to retrieve the PropertyDescriptors for
     * @return an array of {@code PropertyDescriptors} for the given class
     * @throws BeansException if PropertyDescriptor look fails
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException {
        return excludeGetClass(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz));
    }

    /** exclude {@link Object#getClass()} */
    private static PropertyDescriptor[] excludeGetClass(PropertyDescriptor[] descriptors) {
        return Arrays.stream(descriptors).filter(descriptor -> !descriptor.getReadMethod().equals(GET_CLASS_METHOD)).toArray(PropertyDescriptor[]::new);
    }

    /**
     * similar to {@link #getPropertyDescriptor(Class, String)},
     * but the return value is required
     *
     * @param clazz        the Class to retrieve the PropertyDescriptor for
     * @param propertyName the name of the property
     * @return the corresponding PropertyDescriptor
     * @throws BeansException if PropertyDescriptor lookup fails
     */
    public static PropertyDescriptor getRequiredPropertyDescriptor(Class<?> clazz, String propertyName) throws BeansException {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
        if (propertyDescriptor == null) throwPropertyNotFoundException(clazz, propertyName);
        return propertyDescriptor;
    }

    static void throwPropertyNotFoundException(Class<?> entityClass, String propertyName) {
        throw new IllegalArgumentException("can't found property['" + propertyName + "'] on class[" + entityClass.getName() + "]");
    }

    /**
     * get all property name of a class
     *
     * @param clazz the Class to retrieve the property name for
     * @return an array of property name
     * @throws BeansException if PropertyDescriptor lookup fails
     */
    public static String[] getPropertyNames(Class<?> clazz) throws BeansException {
        return Arrays.stream(getPropertyDescriptors(clazz)).map(FeatureDescriptor::getName).toArray(String[]::new);
    }

    /**
     * get property value of a bean by property name,
     * property name must be valid
     *
     * @param bean         a bean
     * @param propertyName a property name which must be valid
     * @return the property value of the special property name
     */
    @Nullable
    public static Object getPropertyValue(Object bean, String propertyName) {
        PropertyDescriptor propertyDescriptor = getRequiredPropertyDescriptor(bean.getClass(), propertyName);
        Method readMethod = propertyDescriptor.getReadMethod();
        if (!readMethod.isAccessible()) readMethod.setAccessible(true);
        return ReflectionUtils.invokeMethod(readMethod, bean);
    }

    /**
     * set property value of a bean by property name, property name must be valid
     *
     * @param bean          a java bean
     * @param propertyName  a property name which must be valid
     * @param propertyValue the property value of the special property name
     */
    public static void setPropertyValue(Object bean, String propertyName, @Nullable Object propertyValue) {
        PropertyDescriptor propertyDescriptor = getRequiredPropertyDescriptor(bean.getClass(), propertyName);
        ReflectionUtils.invokeMethod(propertyDescriptor.getWriteMethod(), bean, propertyValue);
    }

    public static void copyProperties(Object source, Object target, BiPredicate<String, Object> excludedProperty) throws BeansException {
        PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (excludedProperty == null || !excludedProperty.test(sourcePd.getName(), value)) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * convert to {@link Map}, property name as key, property value as value
     *
     * @param bean                a java bean
     * @param includePropertyName used to filter property by name,
     *                            return true means include, otherwise means exclude, null means all
     * @return a map properties
     * @see #map(Object, BiPredicate)
     */
    public static Map<String, Object> map(Object bean, @Nullable Predicate<String> includePropertyName) {
        Map<String, Object> properties = new LinkedHashMap<>();
        String propertyName;
        PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
        for (PropertyDescriptor descriptor : descriptors) {
            propertyName = descriptor.getName();
            if (includePropertyName == null || includePropertyName.test(propertyName)) {
                properties.put(propertyName, ReflectionUtils.invokeMethod(descriptor.getReadMethod(), bean));
            }
        }
        return properties;
    }

    /**
     * similar to {@link #map(Object, Predicate)},
     * but {@code includeProperty} use property name and value to test
     *
     * @param bean            a java bean
     * @param includeProperty used to filter property by name,
     *                        return true means include, otherwise means exclude, null means all
     * @return a map properties
     */
    public static Map<String, Object> map(Object bean, BiPredicate<String, Object> includeProperty) {
        Map<String, Object> properties = new LinkedHashMap<>();
        String propertyName;
        Object propertyValue;
        PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
        for (PropertyDescriptor descriptor : descriptors) {
            propertyName = descriptor.getName();
            propertyValue = ReflectionUtils.invokeMethod(descriptor.getReadMethod(), bean);
            if (includeProperty == null || includeProperty.test(propertyName, propertyValue)) {
                properties.put(propertyName, propertyValue);
            }
        }
        return properties;
    }

    /**
     * similar to {@link #map(Object, Predicate)}, but include all properties
     *
     * @param bean a java bean
     * @return a map properties
     */
    public static Map<String, Object> map(Object bean) {
        return map(bean, (Predicate<String>) null);
    }


    /**
     * similar to {@link EnumUtils#map(Enum[], String, String)},
     * but support object collection
     *
     * @param beans         a java bean collection
     * @param keyProperty   a property in bean as key of map
     * @param valueProperty a property in bean as value of map
     * @param <K>           the type of keyProperty'value
     * @param <V>           the type of valueProperty'value
     * @return the converted map
     */
    public static <K, V> Map<K, V> map(Collection<?> beans, String keyProperty, String valueProperty) {
        return EnumUtils._map(beans, keyProperty, Objects.requireNonNull(valueProperty));
    }

    /**
     * similar to {@link #map(Collection, String, String)},
     * but the map value is the element of {@code beans}
     *
     * @param beans       a java bean collection
     * @param keyProperty a property in bean as key of map
     * @param <K>         the type of keyProperty'value
     * @param <V>         the type of valueProperty'value
     * @return the converted map
     */
    public static <K, V> Map<K, V> map(Collection<V> beans, String keyProperty) {
        return EnumUtils._map(beans, keyProperty, null);
    }

    /**
     * convert to a instance of targetClass
     *
     * @deprecated use {@link #map(Object, Class)} instead
     */
    @Deprecated
    public static <T> T toSubclass(Object source, Class<T> targetClass) {
        return map(source, targetClass);
    }

    /** convert to a instance of targetClass */
    public static <T> T map(Object source, Class<T> targetClass) {
        T target = BeanUtils.instantiate(targetClass);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /** replace the element of collection to subclass instance */
    public static <T> Stream<T> replace(Collection<?> beans, Class<T> targetClass) {
        return beans.stream().map(s -> toSubclass(s, targetClass));
    }

    /** similar to {@link #replace(Collection, Class)}, but return {@link Collection} */
    public static <T, C extends Collection<T>> C replace(Collection<?> beans, Class<T> targetClass, Supplier<C> collectionFactory) {
        return replace(beans, targetClass).collect(Collectors.toCollection(collectionFactory));
    }

    /** similar to {@link #replace(Collection, Class)}, but return {@link List} */
    public static <T> List<T> replaceAsList(Collection<?> beans, Class<T> targetClass) {
        return replace(beans, targetClass, ArrayList::new);
    }

}
