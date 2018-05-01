package com.github.peacetrue.associate;

/**
 * a data source to get associated value.
 * the associated value maybe object or property.
 *
 * @param <I> the type of associated object id
 * @param <D> the type of associated data
 * @param <R> the type of associated value
 * @author xiayx
 * @see SameAssociatedSource
 * @see CollectionAssociatedSource
 * @see SameCollectionAssociatedSource
 */
public interface AssociatedSource<I, D, R> {

    /**
     * get associated data by id
     *
     * @param id the associated object id
     * @return associated data
     */
    D getById(I id);

    /**
     * get associated value from associated data
     *
     * @param data associated data
     * @return associated value
     */
    R format(D data);

}
