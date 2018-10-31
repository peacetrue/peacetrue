package com.github.peacetrue.tree;

/**
 * @author xiayx
 */
public class ChildrenTreeImpl<M extends Tree.ParentNode<M>> extends ChildrenTree<M> {

    @Override
    protected boolean isElderOf(M parent, M child) {
        return child.getParent().equals(parent);
    }

}
