package com.github.peacetrue.tree.extractor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author xiayx
 */
public class PredicateOneExtractor<T> implements Extractor.PredicateExtractor<T, Optional<T>> {

    private Predicate<T> predicate;
    private T target;

    public PredicateOneExtractor(Predicate<T> predicate) {
        this.predicate = Objects.requireNonNull(predicate);
    }

    @Override
    public boolean test(T t) {
        boolean test = predicate.test(t);
        if (test) target = t;
        return test;
    }

    public Optional<T> getExtract() {
        return Optional.ofNullable(target);
    }
}
