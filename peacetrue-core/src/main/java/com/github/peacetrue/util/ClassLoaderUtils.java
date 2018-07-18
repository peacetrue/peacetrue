package com.github.peacetrue.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类加载器工具类
 *
 * @author xiayx
 */
public abstract class ClassLoaderUtils {

    private static final Method DEFINE_CLASS = getDefineClassMethod();

    private static Method getDefineClassMethod() {
        try {
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            return defineClass;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 定义类
     *
     * @param classLoader 类加载器
     * @param name        类名
     * @param bytes       类字节码
     * @return 被定义的类
     */
    public static Class defineClass(ClassLoader classLoader, String name, byte[] bytes) {
        try {
            return (Class) DEFINE_CLASS.invoke(classLoader, new Object[]{name, bytes, 0, bytes.length});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 定义类
     *
     * @param name  类名
     * @param bytes 类字节码
     * @return 被定义的类
     */
    public static Class defineClass(String name, byte[] bytes) {
        return defineClass(ClassLoaderUtils.class.getClassLoader(), name, bytes);
    }


}
