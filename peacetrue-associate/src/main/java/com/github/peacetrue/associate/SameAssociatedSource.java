package com.github.peacetrue.associate;

/**
 * the associated data is equals to associated value
 *
 * @param <I> the type of associated object id
 * @param <D> the type of associated data
 * @author xiayx
 * @see AssociatedSource
 * @see CollectionAssociatedSource
 * @see SameCollectionAssociatedSource
 */
public interface SameAssociatedSource<I, D> extends AssociatedSource<I, D, D> {

    /**
     * the associated data is equals to associated value
     *
     * @param data associated data
     * @return associated value
     */
    default D format(D data) {
        return data;
    }
}
