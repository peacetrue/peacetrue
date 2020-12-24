package com.github.peacetrue.util;

import java.util.Objects;

/**
 * Variable used in lambda expression should be final or effectively final
 * <pre>
 * //error
 * int i = 0;
 * new Thread(() -> i++).start();
 *
 * //correct
 * Ref&lt;Integer&gt; ref = new Ref&lt;&gt;(1);
 * new Thread(() -> ref.current++).start();
 * </pre>
 *
 * @author : xiayx
 * @since : 2020-12-19 20:53
 **/
public class Ref<T> {

    public T current;

    public Ref(T current) {
        this.current = Objects.requireNonNull(current);
    }
}
