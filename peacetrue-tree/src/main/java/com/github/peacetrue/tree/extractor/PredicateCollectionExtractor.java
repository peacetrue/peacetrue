package com.github.peacetrue.tree.extractor;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xiayx
 */
public class PredicateCollectionExtractor<N, T extends Collection<N>> implements Extractor.PredicateExtractor<N, T> {

    private Predicate<N> predicate;
    private T target;

    public PredicateCollectionExtractor(Predicate<N> predicate, T target) {
        this.predicate = Objects.requireNonNull(predicate);
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public boolean test(N t) {
        boolean test = predicate.test(t);
        if (test) target.add(t);
        return test;
    }

    public T getExtract() {
        return target;
    }
}
