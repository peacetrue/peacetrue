package com.github.peacetrue.associate;

/**
 * the associated data is equals to associated value
 *
 * @param <I> the type of associated object id
 * @param <D> the type of associated data
 * @author xiayx
 * @see AssociatedSource
 * @see SameAssociatedSource
 * @see CollectionAssociatedSource
 */
public interface SameCollectionAssociatedSource<I, D> extends CollectionAssociatedSource<I, D, D>, SameAssociatedSource<I, D> {

}
