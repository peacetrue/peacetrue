package com.github.peacetrue.tree.iterate;

import com.github.peacetrue.tree.Tree;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * 树迭代接口
 *
 * @author xiayx
 */
public interface IterableTree<T> {

    /** 获取根节点 */
    Optional<T> getRoot();

    /** 查找子节点 */
    List<T> findChildren(T node);

    /** 从{@link Tree.ChildrenNode}构造 */
    static <T extends Tree.ChildrenNode<T>> IterableTree<T> build(T node) {
        Assert.notNull(node);
        return new IterableTree<T>() {
            public Optional<T> getRoot() {
                return Optional.of(node);
            }

            public List<T> findChildren(T node) {
                return node.getChildren();
            }
        };
    }

}
