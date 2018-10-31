package com.github.peacetrue.tree;

/**
 * class hierarchy, concurrency not supported
 *
 * @author xiayx
 */
public class ClassTree extends ChildrenTree<Class<?>> {

    @Override
    protected void initRoot() {
        super.initRoot();
        root.setMaterial(Object.class);
    }

    @Override
    protected boolean equals(Class<?> one, Class<?> another) {
        return one == another;
    }

    protected boolean isElderOf(Class<?> parent, Class<?> child) {
        return parent.isAssignableFrom(child) && !equals(parent, child);
    }

}
