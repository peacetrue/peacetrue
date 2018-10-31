package com.github.peacetrue.tree.node;

import com.github.peacetrue.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiayx
 */
public abstract class ChildrenNode<T extends ChildrenNode<T>> implements Tree.ChildrenNode<T> {

    private List<T> children;

    public ChildrenNode() {
        this(new ArrayList<>());
    }

    public ChildrenNode(List<T> children) {
        this.children = children;
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
