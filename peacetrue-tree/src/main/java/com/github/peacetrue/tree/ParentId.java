package com.github.peacetrue.tree;

/**
 * 父节点主键
 *
 * @author xiayx
 */
public interface ParentId<T> {

    /** 父级主键为null的节点认为是根节点 */
    RootPredicate<ParentId> NULL_AS_ROOT = node -> node.getParentId() == null;

    /** 获取父节点主键 */
    T getParentId();
}
