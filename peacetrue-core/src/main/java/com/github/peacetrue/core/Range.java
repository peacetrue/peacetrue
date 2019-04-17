package com.github.peacetrue.core;

import java.io.Serializable;

/**
 * copy from {@link org.springframework.data.domain.Range}
 * to support {@link org.springframework.beans.BeanUtils#instantiate}
 */
public class Range<T> implements Serializable {

    /** 类型限定为可比较的，并添加contains方法 */
    public static class Comparable<T extends java.lang.Comparable<T>> extends Range<T> {
        public Comparable() {}

        public Comparable(T lowerBound, T upperBound) {super(lowerBound, upperBound);}

        public Comparable(T lowerBound, T upperBound, boolean lowerInclusive, boolean upperInclusive) {super(lowerBound, upperBound, lowerInclusive, upperInclusive);}

        public boolean contains(T value) {
            boolean greaterThanLowerBound = getLowerBound() == null || (isLowerInclusive() ? getLowerBound().compareTo(value) <= 0 : getLowerBound().compareTo(value) < 0);
            boolean lessThanUpperBound = getUpperBound() == null || (isUpperInclusive() ? getUpperBound().compareTo(value) >= 0 : getUpperBound().compareTo(value) > 0);
            return greaterThanLowerBound && lessThanUpperBound;
        }
    }

    /** 类型设置为日期，spring参数注入时不能使用泛化参数类型 */
    public static class Date extends Comparable<java.util.Date> {
        public Date() {}

        public Date(java.util.Date lowerBound, java.util.Date upperBound) {
            super(lowerBound, upperBound);
        }

        public Date(java.util.Date lowerBound, java.util.Date upperBound, boolean lowerInclusive, boolean upperInclusive) {
            super(lowerBound, upperBound, lowerInclusive, upperInclusive);
        }
    }

    /**
     * The lower bound of the range.
     */
    private T lowerBound;

    /**
     * The upper bound of the range.
     */
    private T upperBound;

    /**
     * Whether the lower bound is considered inclusive.
     */
    private boolean lowerInclusive;

    /**
     * Whether the lower bound is considered inclusive.
     */
    private boolean upperInclusive;

    public Range() {
    }

    /**
     * Creates a new {@link Range} with the given lower and upper bound. Treats the given values as inclusive bounds. Use
     * {@link #Range(java.lang.Comparable, java.lang.Comparable, boolean, boolean)} to configure different bound behavior.
     *
     * @param lowerBound can be {@literal null} in case upperBound is not {@literal null}.
     * @param upperBound can be {@literal null} in case lowerBound is not {@literal null}.
     * @see #Range(java.lang.Comparable, java.lang.Comparable, boolean, boolean)
     */
    public Range(T lowerBound, T upperBound) {
        this(lowerBound, upperBound, true, true);
    }

    /**
     * Creates a new {@link Range} with the given lower and upper bound as well as the given inclusive/exclusive
     * semantics.
     *
     * @param lowerBound     can be {@literal null}.
     * @param upperBound     can be {@literal null}.
     * @param lowerInclusive
     * @param upperInclusive
     */
    public Range(T lowerBound, T upperBound, boolean lowerInclusive, boolean upperInclusive) {

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.lowerInclusive = lowerInclusive;
        this.upperInclusive = upperInclusive;
    }


    public T getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(T lowerBound) {
        this.lowerBound = lowerBound;
    }

    public T getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(T upperBound) {
        this.upperBound = upperBound;
    }

    public boolean isLowerInclusive() {
        return lowerInclusive;
    }

    public void setLowerInclusive(boolean lowerInclusive) {
        this.lowerInclusive = lowerInclusive;
    }

    public boolean isUpperInclusive() {
        return upperInclusive;
    }

    public void setUpperInclusive(boolean upperInclusive) {
        this.upperInclusive = upperInclusive;
    }
}
