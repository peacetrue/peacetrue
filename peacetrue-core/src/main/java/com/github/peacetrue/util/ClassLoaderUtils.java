package com.github.peacetrue.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            throw new IllegalStateException("not expected exception", e);
        }
    }

    /**
     * 加载字节码内容
     *
     * @param classLoader 类加载器，用于获取类的字节码
     * @param className   类名称，例如：com.github.peacetrue.util.ClassLoaderUtils
     * @return 类的字节码
     */
    public static byte[] loadClass(ClassLoader classLoader, String className) {
        className = toBytecodePath(className);
        InputStream stream = classLoader.getResourceAsStream(className);
        try {
            return toByteArray(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException("can't load class '" + className + "'", e);
        }
    }


    /** 基于{@link #loadClass(ClassLoader, String)}设置了默认的{@link ClassLoader} */
    public static byte[] loadClass(String className) {
        return ClassLoaderUtils.loadClass(ClassLoaderUtils.class.getClassLoader(), className);
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // you can configure the buffer size
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length); //copy streams
        }
        in.close(); // call this in a finally block

        return out.toByteArray();
    }

    /**
     * 定义类
     *
     * @param classLoader 类加载器
     * @param className   类名称，例如：com.github.peacetrue.util.ClassLoaderUtils
     * @param bytes       类字节码数组，可以使用{@link #loadClass(String)}的返回值
     * @return 被定义的类
     * @see #loadClass(String)
     */
    public static Class defineClass(ClassLoader classLoader, String className, byte[] bytes) {
        try {
            return (Class) DEFINE_CLASS.invoke(classLoader, new Object[]{className, bytes, 0, bytes.length});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("can't define class '" + className + "'", e);
        }
    }

    /** 基于{@link #defineClass(ClassLoader, String, byte[])}设置了默认的{@link ClassLoader} */
    public static Class defineClass(String className, byte[] bytes) {
        return defineClass(ClassLoaderUtils.class.getClassLoader(), className, bytes);
    }

    /**
     * 转成类名成类字节码路径
     *
     * @param className 类名，例如：com.github.peacetrue.util.ClassLoaderUtils
     * @return 类字节码路径，例如：com/github/peacetrue/util/ClassLoaderUtils.class
     */
    public static String toBytecodePath(String className) {
        return toPath(className) + ".class";
    }

    /**
     * 转成类名成类路径
     *
     * @param className 类名，例如：com.github.peacetrue.util.ClassLoaderUtils
     * @return 类路径，例如：com/github/peacetrue/util/ClassLoaderUtils
     */
    public static String toPath(String className) {
        return className.replace('.', '/');
    }


}
