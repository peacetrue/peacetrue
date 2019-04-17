package com.github.peacetrue.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author xiayx
 */
public abstract class ComparableUtils {

    /** 获取起止范围之间的值，如果值是引用类型，increase函数需要每次都返回一个新的值，如果基于原值修改，返回的整个集合中都是同一个值 */
    public static <T extends Comparable<T>> List<T> findValueBetweenRange(T startInclusive, T endInclusive, UnaryOperator<T> increase) {
        return findValueBetweenRange(startInclusive, endInclusive, increase, Function.identity());
    }

    /** 获取起止范围之间的值，increase函数可以不必每次都返回一个新的值, mapper函数需要每次都返回一个新的值 */
    public static <T extends Comparable<T>, R> List<R> findValueBetweenRange(T startInclusive, T endInclusive, UnaryOperator<T> increase, Function<T, R> mapper) {
        List<R> set = new LinkedList<>();
        while (startInclusive.compareTo(endInclusive) <= 0) {
            set.add(mapper.apply(startInclusive));
            startInclusive = increase.apply(startInclusive);
        }
        return set;
    }

}
