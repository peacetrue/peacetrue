package com.github.peacetrue.tree;

import com.github.peacetrue.tree.node.MaterialChildrenNode;

/**
 * abstract tree
 *
 * @author xiayx
 */
public abstract class ChildrenTree<M> extends VirtualTree<MaterialChildrenNode<M>, M> implements Tree<M> {

    @Override
    protected MaterialChildrenNode<M> initNode(M node) {
        return new MaterialChildrenNode<>(node);
    }
}
