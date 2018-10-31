package com.github.peacetrue.tree.node;

import java.util.List;

/**
 * @author xiayx
 */
public class MaterialChildrenNode<M> extends ChildrenNode<MaterialChildrenNode<M>> implements Material<M> {

    private M material;

    public MaterialChildrenNode() {
    }

    public MaterialChildrenNode(M material) {
        this.material = material;
    }

    public MaterialChildrenNode(M material, List<MaterialChildrenNode<M>> children) {
        super(children);
        this.material = material;
    }

    @Override
    public String toString() {
        return String.valueOf(material);
    }

    public M getMaterial() {
        return material;
    }

    public void setMaterial(M material) {
        this.material = material;
    }
}
