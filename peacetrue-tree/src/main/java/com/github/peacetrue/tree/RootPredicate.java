package com.github.peacetrue.tree;

/**
 * 根节点判断
 *
 * @author xiayx
 */
public interface RootPredicate<T> {
    /** 指定节点是否根节点 */
    boolean isRoot(T node);
}
