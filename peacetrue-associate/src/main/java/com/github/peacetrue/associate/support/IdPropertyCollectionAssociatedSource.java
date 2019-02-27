package com.github.peacetrue.associate.support;

import com.github.peacetrue.associate.CollectionAssociatedSource;
import com.github.peacetrue.spring.util.BeanUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 主键属性集合关联源
 *
 * @author xiayx
 */
public abstract class IdPropertyCollectionAssociatedSource<I, D, R> implements CollectionAssociatedSource<I, D, R> {

    /** 主键属性名称 */
    protected String idPropertyName;

    protected IdPropertyCollectionAssociatedSource() {
        this("id");
    }

    protected IdPropertyCollectionAssociatedSource(String idPropertyName) {
        this.idPropertyName = Objects.requireNonNull(idPropertyName);
    }

    @Override
    public abstract Collection<D> findCollectionAssociate(Collection<I> ids);

    @Override
    @SuppressWarnings("unchecked")
    public I resolveId(D data) {
        return (I) BeanUtils.getPropertyValue(data, idPropertyName);
    }

    @Override
    public abstract R format(D data);
}
