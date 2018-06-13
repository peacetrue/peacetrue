package com.github.peacetrue.core;

/**
 * a range
 *
 * @author xiayx
 */
public class Range<T extends Comparable<T>> {

    private T minimum;
    private T maximum;

    public Range() {
    }

    public Range(T minimum, T maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public T getMinimum() {
        return minimum;
    }

    public void setMinimum(T minimum) {
        this.minimum = minimum;
    }

    public T getMaximum() {
        return maximum;
    }

    public void setMaximum(T maximum) {
        this.maximum = maximum;
    }
}
