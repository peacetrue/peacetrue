package com.github.peacetrue.tree.extractor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xiayx
 */
public class FilterConsumerCollectionExtractor<N, T extends Collection<N>> extends ConsumerCollectionExtractor<N, T> {

    private Predicate<N> predicate;

    public FilterConsumerCollectionExtractor(Predicate<N> predicate, T target) {
        super(target);
        this.predicate = Objects.requireNonNull(predicate);
    }

    public static <N> FilterConsumerCollectionExtractor<N, List<N>> linkedList(Predicate<N> predicate) {
        return new FilterConsumerCollectionExtractor<>(predicate, new LinkedList<>());
    }

    @Override
    public void accept(N t) {
        if (predicate.test(t)) super.accept(t);
    }

}
