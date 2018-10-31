package com.github.peacetrue.tree;

/** 树节点关系判断 */
public interface RelationPredicate<T> {

    /** 节点parent是否节点child的父节点 */
    boolean isParentOf(T parent, T child);

    /** 节点child是否节点parent的子节点 */
    default boolean isChildOf(T child, T parent) {
        return isParentOf(parent, child);
    }
}
