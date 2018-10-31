package com.github.peacetrue.associate;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * a data source to get collection associated value.
 * the associated value maybe object or property.
 *
 * @param <I> the type of associated object id
 * @param <D> the type of associated data
 * @param <R> the type of associated value
 * @author xiayx
 * @see AssociatedSource
 * @see SameAssociatedSource
 * @see SameCollectionAssociatedSource
 */
public interface CollectionAssociatedSource<I, D, R> extends AssociatedSource<I, D, R> {

    /** get one from {@link #findCollectionAssociate(Collection)} */
    default D findAssociate(I id) {
        return findCollectionAssociate(Collections.singleton(id)).iterator().next();
    }

    /**
     * get collection associated data by collection id
     *
     * @param ids the collection associated object id
     * @return collection associated data
     */
    Collection<D> findCollectionAssociate(Collection<I> ids);

    /**
     * get associated object id from associated data
     *
     * @param data associated data
     * @return associated object id
     */
    I resolveId(D data);

    /**
     * similar to {@link AssociatedSource#format} for collection
     *
     * @param data associated data
     * @return associated value
     */
    default Collection<R> format(Collection<D> data) {
        return data.stream().map(this::format).collect(Collectors.toList());
    }
}
