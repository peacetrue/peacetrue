package com.github.peacetrue.tree.node;

import com.github.peacetrue.tree.Tree;

import java.util.List;

/**
 * @author xiayx
 */
public abstract class Node<T extends Node<T>> extends ChildrenNode<T> implements Tree.Node<T> {

    private T parent;

    public Node() {
    }

    @SuppressWarnings("unchecked")
    public Node(T parent) {
        this.parent = parent;
        this.parent.getChildren().add((T) this);
    }

    public Node(T parent, List<T> children) {
        super(children);
        this.parent = parent;
    }

    @Override
    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }
}
