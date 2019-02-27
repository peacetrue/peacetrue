package com.github.peacetrue.associate.support;

import com.github.peacetrue.spring.util.BeanUtils;

import java.util.Objects;

/**
 * 关联的属性集合关联源
 *
 * @author xiayx
 */
public abstract class AssociatedPropertyCollectionAssociatedSource<I, D, R> extends IdPropertyCollectionAssociatedSource<I, D, R> {

    /** 关联的属性名称 */
    protected String associatedPropertyName;

    public AssociatedPropertyCollectionAssociatedSource(String associatedPropertyName) {
        this.associatedPropertyName = Objects.requireNonNull(associatedPropertyName);
    }

    public AssociatedPropertyCollectionAssociatedSource(String idPropertyName, String associatedPropertyName) {
        super(idPropertyName);
        this.associatedPropertyName = Objects.requireNonNull(associatedPropertyName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public R format(D data) {
        return (R) BeanUtils.getPropertyValue(data, associatedPropertyName);
    }
}
