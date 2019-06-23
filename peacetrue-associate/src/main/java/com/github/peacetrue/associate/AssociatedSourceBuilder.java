package com.github.peacetrue.associate;

import java.util.Collection;
import java.util.function.Function;

/**
 * a builder for {@link AssociatedSource}
 *
 * @author xiayx
 * @see AssociatedSourceBuilderUtils
 * @see AssociatedSourceBuilderImpl
 */
public interface AssociatedSourceBuilder {

    <I, D, R> AssociatedSource<I, D, R> buildAssociatedSource(Function<I, D> findAssociate, Function<D, R> format);

    default <I, D> AssociatedSource<I, D, D> buildAssociatedSource(Function<I, D> findAssociate) {
        return buildAssociatedSource(findAssociate, Function.identity());
    }

    <I, D, R> CollectionAssociatedSource<I, D, R> buildCollectionAssociatedSource(Function<Collection<I>, Collection<D>> findAssociate,
                                                                                  Function<D, I> resolveId,
                                                                                  Function<D, R> format);

    default <I, D> CollectionAssociatedSource<I, D, D> buildCollectionAssociatedSource(Function<Collection<I>, Collection<D>> findAssociate,
                                                                                       Function<D, I> resolveId) {
        return buildCollectionAssociatedSource(findAssociate, resolveId, Function.identity());
    }
}
