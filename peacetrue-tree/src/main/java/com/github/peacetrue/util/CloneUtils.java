package com.github.peacetrue.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class CloneUtils {

    private static Method findCloneMethod(Class<?> clazz) {
        Method clone = ReflectionUtils.findMethod(clazz, "clone");
        if (!Modifier.isPublic(clone.getModifiers())) clone.setAccessible(true);
        return clone;
    }

    /**
     * 克隆对象，封装类型转换和异常。
     * <p>
     * 通常实现Cloneable后代码结构如下：
     * <pre>
     * class A implements Cloneable {
     *      public Object clone() throws CloneNotSupportedException {
     *          return super.clone();
     *      }
     * }
     * </pre>
     * 其中，有两个问题：
     * <ul>
     * <li>返回对象是Object，调用时需要强制转换</li>
     * <li>抛出CloneNotSupportedException，调用时需要捕获</li>
     * </ul>
     * 修复以上问题后代码结构如下：
     * <pre>
     * class A implements Cloneable {
     *      public A clone() {
     *          try {
     *              return (A) super.clone();
     *          } catch (CloneNotSupportedException e) {
     *              throw new IllegalStateException(e);
     *          }
     *      }
     * }
     * </pre>
     * 每一个继承Cloneable的类都需要按以上模板实现clone方法较为繁琐，
     * 所以提供了此工具方法实现克隆，而不要求实现clone方法。
     * <pre>
     *  class A implements Cloneable {}
     *  CloneUtils.clone(new A());
     * </pre>
     *
     * @param obj 欲克隆的对象
     * @param <T> 对象类型
     * @return 克隆得到的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T obj) {
        if (!(obj instanceof Cloneable)) {
            throw new IllegalArgumentException("the arg " + obj + " not support Cloneable");
        }

        try {
            return (T) findCloneMethod(obj.getClass()).invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }
}