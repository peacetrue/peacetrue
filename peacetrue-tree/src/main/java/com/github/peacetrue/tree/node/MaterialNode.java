package com.github.peacetrue.tree.node;

import java.util.List;

/**
 * @author xiayx
 */
public class MaterialNode<T> extends Node<MaterialNode<T>> implements Material<T> {

    private T material;

    public MaterialNode() {
    }

    public MaterialNode(T material) {
        this.material = material;
    }

    public MaterialNode(MaterialNode<T> parent, T material) {
        super(parent);
        this.material = material;
    }

    public MaterialNode(MaterialNode<T> parent, T material, List<MaterialNode<T>> children) {
        super(parent, children);
        this.material = material;
    }

    public T getMaterial() {
        return material;
    }

    public void setMaterial(T material) {
        this.material = material;
    }
}
