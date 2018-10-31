package com.github.peacetrue.tree.menu;

import com.github.peacetrue.tree.Tree;
import com.github.peacetrue.tree.node.Node;

import java.util.ArrayList;

/**
 * 菜单类，基于父子节点引用关联：{@link #getParent()}和{@link #getChildren()}
 *
 * @author xiayx
 */
public class NodeMenu extends Node<NodeMenu> implements Cloneable, Tree.ParentNodeAware<NodeMenu> {

    private Long id;
    private Integer sort;

    public NodeMenu(Long id) {
        this.id = id;
    }

    public NodeMenu(NodeMenu parent, Long id) {
        super(parent);
        this.id = id;
    }


    @Override
    public String toString() {
        return "NodeMenu{" +
                "id=" + id +
                ", parent=" + getParent() +
                "} ";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        NodeMenu clone = (NodeMenu) super.clone();
        clone.setParent(null);
        clone.setChildren(new ArrayList<>());
        return clone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}



