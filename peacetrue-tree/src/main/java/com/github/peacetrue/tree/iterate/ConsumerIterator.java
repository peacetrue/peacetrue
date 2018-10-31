package com.github.peacetrue.tree.iterate;

import com.github.peacetrue.tree.Tree;
import com.github.peacetrue.util.StreamUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 遍历器，不可中断遍历过程。
 *
 * @author xiayx
 * @see PredicateIterator
 */
public interface ConsumerIterator {

    /** 从根节点到叶子节点 */
    ConsumerIterator ASC = new Abstract() {
        protected <T extends Tree.ChildrenNode<T>> void each(T node, BiConsumer<T, Integer> consumer, int level) {
            consumer.accept(node, level);
            List<T> children = node.getChildren();
            if (children != null) children.forEach(child -> each(child, consumer, level + 1));
        }

        @Override
        protected <T> void each(IterableTree<T> tree, T node, BiConsumer<T, Integer> consumer, int level) {
            consumer.accept(node, level);
            List<T> children = tree.findChildren(node);
            if (children != null) children.forEach(child -> each(tree, child, consumer, level + 1));
        }
    };

    /** 从叶子节点到根节点 */
    ConsumerIterator DESC = new Abstract() {
        protected <T extends Tree.ChildrenNode<T>> void each(T node, BiConsumer<T, Integer> consumer, int level) {
            List<T> children = node.getChildren();
            if (children != null) children.forEach(child -> each(child, consumer, level + 1));
            consumer.accept(node, level);
        }

        @Override
        protected <T> void each(IterableTree<T> tree, T node, BiConsumer<T, Integer> consumer, int level) {
            List<T> children = tree.findChildren(node);
            if (children != null) children.forEach(child -> each(tree, child, consumer, level + 1));
            consumer.accept(node, level);
        }
    };

    /** 根节点所在层级 */
    int ROOT_LEVEL = 0;

    /**
     * 遍历树节点
     *
     * @param node     欲遍历的节点
     * @param consumer 遍历过程中欲进行的处理，第一个参数是节点，第二个参数是节点所在层级
     * @param <T>      树节点类型
     */
    <T extends Tree.ChildrenNode<T>> void each(T node, BiConsumer<T, Integer> consumer);

    /**
     * 遍历树节点
     *
     * @param node     欲遍历的节点
     * @param consumer 遍历过程中欲进行的处理，参数是节点
     * @param <T>      树节点类型
     */
    default <T extends Tree.ChildrenNode<T>> void each(T node, Consumer<T> consumer) {
        each(node, StreamUtils.toBiConsumer(consumer));
    }

    /**
     * 遍历树
     *
     * @param tree     欲遍历的树
     * @param consumer 遍历过程中欲进行的处理，第一个参数是节点，第二个参数是节点所在层级
     * @param <T>      树节点类型
     */
    <T> void each(IterableTree<T> tree, BiConsumer<T, Integer> consumer);

    /**
     * 遍历树
     *
     * @param tree     欲遍历的树
     * @param consumer 遍历过程中欲进行的处理，参数是节点
     * @param <T>      树节点类型
     */
    default <T> void each(IterableTree<T> tree, Consumer<T> consumer) {
        each(tree, StreamUtils.toBiConsumer(consumer));
    }

    abstract class Abstract implements ConsumerIterator {

        @Override
        public <T extends Tree.ChildrenNode<T>> void each(T node, BiConsumer<T, Integer> consumer) {
            each(node, consumer, ROOT_LEVEL);
        }

        protected abstract <T extends Tree.ChildrenNode<T>> void each(T node, BiConsumer<T, Integer> consumer, int level);

        @Override
        public <T> void each(IterableTree<T> tree, BiConsumer<T, Integer> consumer) {
            Optional<T> node = tree.getRoot();
            if (!node.isPresent()) return;
            each(tree, node.get(), consumer, ROOT_LEVEL);
        }

        protected abstract <T> void each(IterableTree<T> tree, T node, BiConsumer<T, Integer> consumer, int level);
    }


}
