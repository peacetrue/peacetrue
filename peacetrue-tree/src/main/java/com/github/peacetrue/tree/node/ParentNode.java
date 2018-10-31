package com.github.peacetrue.tree.node;

import com.github.peacetrue.tree.Tree;

/**
 * @author xiayx
 */
public abstract class ParentNode<T extends Tree.ParentNode<T>> implements Tree.ParentNode<T> {

    private T parent;

    public ParentNode() {
    }

    public ParentNode(T parent) {
        this.parent = parent;
    }

    @Override
    public T getParent() {
        return parent;
    }
}
