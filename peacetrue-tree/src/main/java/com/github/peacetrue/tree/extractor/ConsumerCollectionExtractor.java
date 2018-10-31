package com.github.peacetrue.tree.extractor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiayx
 */
public class ConsumerCollectionExtractor<N, T extends Collection<N>> implements Extractor.ConsumerExtractor<N, T> {

    private T target;

    public ConsumerCollectionExtractor(T target) {
        this.target = Objects.requireNonNull(target);
    }

    public static <N> ConsumerCollectionExtractor<N, List<N>> linkedList() {
        return new ConsumerCollectionExtractor<>(new LinkedList<>());
    }

    @Override
    public void accept(N t) {
        target.add(t);
    }

    public T getExtract() {
        return target;
    }
}
