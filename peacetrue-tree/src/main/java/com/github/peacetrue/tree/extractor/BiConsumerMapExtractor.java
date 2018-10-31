package com.github.peacetrue.tree.extractor;

import com.github.peacetrue.util.StreamUtils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xiayx
 */
public class BiConsumerMapExtractor<N, T extends Map<N, Integer>> implements Extractor.BiConsumerExtractor<N, T> {

    private Predicate<N> predicate;
    private T target;

    public BiConsumerMapExtractor(T target) {
        this(StreamUtils::alwaysTrue, target);
    }

    public BiConsumerMapExtractor(Predicate<N> predicate, T target) {
        this.predicate = Objects.requireNonNull(predicate);
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public void accept(N t, Integer level) {
        if (predicate.test(t)) target.put(t, level);
    }

    public T getExtract() {
        return target;
    }
}
