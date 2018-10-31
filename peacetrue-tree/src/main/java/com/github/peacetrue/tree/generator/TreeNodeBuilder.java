package com.github.peacetrue.tree.generator;

/**
 * 树节点构造器
 *
 * @author xiayx
 */
public interface TreeNodeBuilder<T> {

    /**
     * 构造树节点
     *
     * @param parent 父节点
     * @param index  生成的树节点在父节点下索引
     * @param level  层级，从0开始
     * @return 树节点
     */
    T build(T parent, int index, int level);
}
