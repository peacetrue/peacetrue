package com.github.peacetrue.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

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

    /** exclude {@link Object#getClass()} */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException {
        return excludeGetClass(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz));
    }

    /** exclude {@link Object#getClass()} */
    private static PropertyDescriptor[] excludeGetClass(PropertyDescriptor[] descriptors) {
        return Arrays.stream(descriptors).filter(descriptor -> !descriptor.getReadMethod().equals(GET_CLASS_METHOD)).toArray(PropertyDescriptor[]::new);
    }

    /** similar to {@link #getPropertyDescriptor(Class, String)}, but the return value is required */
    public static PropertyDescriptor getRequiredPropertyDescriptor(Class<?> clazz, String propertyName) throws BeansException {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
        if (propertyDescriptor == null) throwPropertyNotFoundException(clazz, propertyName);
        return propertyDescriptor;
    }

    static void throwPropertyNotFoundException(Class<?> entityClass, String propertyName) {
        throw new IllegalArgumentException("can't found property['" + propertyName + "'] on class[" + entityClass.getName() + "]");
    }

    /** get all property name of a class */
    public static String[] getPropertyNames(Class<?> clazz) throws BeansException {
        return Arrays.stream(getPropertyDescriptors(clazz)).map(FeatureDescriptor::getName).toArray(String[]::new);
    }

    /** get property value of a bean by property name, property name must be valid */
    @Nullable
    public static Object getPropertyValue(Object bean, String propertyName) {
        PropertyDescriptor propertyDescriptor = getRequiredPropertyDescriptor(bean.getClass(), propertyName);
        return ReflectionUtils.invokeMethod(propertyDescriptor.getReadMethod(), bean);
    }

    /** set property value of a bean by property name, property name must be valid */
    public static void setPropertyValue(Object bean, String propertyName, @Nullable Object propertyValue) {
        PropertyDescriptor propertyDescriptor = getRequiredPropertyDescriptor(bean.getClass(), propertyName);
        ReflectionUtils.invokeMethod(propertyDescriptor.getWriteMethod(), bean, propertyValue);
    }

    /**
     * convert to {@link Map}, property name as key, property value as value
     *
     * @param bean                a java bean
     * @param includePropertyName used to filter property by name,
     *                            return true means include, otherwise means exclude, null means all
     * @return a map properties, maybe null
     * @see #map(Object, BiPredicate)
     */
    public static Map<String, Object> map(Object bean, @Nullable Predicate<String> includePropertyName) {
        Map<String, Object> properties = new HashMap<>();
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

    /** similar to {@link #map(Object, Predicate)}, but {@code includeProperty} use property name and value to test */
    public static Map<String, Object> map(Object bean, BiPredicate<String, Object> includeProperty) {
        Map<String, Object> properties = new HashMap<>();
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

    /** similar to {@link #map(Object, Predicate)}, but include all properties */
    public static Map<String, Object> map(Object bean) {
        return map(bean, (Predicate<String>) null);
    }

    /** similar to {@link EnumUtils#map(Enum[], String, String)}, but support object collection */
    public static <K, V> Map<K, V> map(Collection<?> beans, String keyProperty, String valueProperty) {
        return EnumUtils._map(beans, keyProperty, Objects.requireNonNull(valueProperty));
    }

    /** similar to {@link #map(Collection, String, String)}, but the map value is element of {@code beans} */
    public static <I, V> Map<I, V> map(Collection<V> beans, String propertyName) {
        return EnumUtils._map(beans, propertyName, null);
    }


}
