package com.github.peacetrue.tree.iterate;

import com.github.peacetrue.tree.Tree;
import com.github.peacetrue.util.StreamUtils;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * 遍历迭代器，可中断遍历过程。
 * <p>
 * 遍历类型: 升序 和 降序, 互斥 和 递进, 满足 和 贪婪.
 * <ul>
 * <li>升序: 从根节点到叶子节点</li>
 * <li>降序：从叶子节点到根节点</li>
 * <li>互斥: 父节点匹配，忽略子节点；父节点不匹配，继续遍历子节点</li>
 * <li>递进: 父节点匹配，继续遍历子节点；父节点不匹配，忽略子节点</li>
 * <li>满足: 匹配任意节点后终止遍历</li>
 * <li>贪婪: 始终尝试匹配更多节点</li>
 * </ul>
 * <p>
 * iterate type: asc and desc, mutex and progressive, satisfied and greedy.
 * <ul>
 * <li>asc: from root to leaf node</li>
 * <li>desc: from leaf node to root</li>
 * <li>mutex: parent matched, ignore children; parent not matched, continue children</li>
 * <li>progressive: parent matched, continue children; parent not matched, ignore children</li>
 * <li>satisfied: any node matched then return</li>
 * <li>greedy: try to match more node</li>
 * </ul>
 *
 * @author xiayx
 * @see ConsumerIterator
 */
public interface PredicateIterator {

    int ROOT_LEVEL = ConsumerIterator.ROOT_LEVEL;

    /**
     * 遍历树节点
     *
     * @param node      欲遍历的节点
     * @param predicate 遍历过程中欲进行的处理，
     *                  第一个参数是节点，第二个参数是节点所在层级，
     *                  返回值true表示继续遍历，false表示中断遍历
     * @param <T>       节点类型
     */
    <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate);

    /**
     * 遍历树节点，等同于{@link #each(Tree.ChildrenNode, BiPredicate)}不需要节点层级时使用
     *
     * @param node      欲遍历的节点
     * @param predicate 遍历过程中欲进行的处理，
     *                  第一个参数是节点
     *                  返回值true表示继续遍历，false表示中断遍历
     * @param <T>       节点类型
     */
    default <T extends Tree.ChildrenNode<T>> boolean each(T node, Predicate<T> predicate) {
        return each(node, StreamUtils.toBiPredicate(predicate));
    }

    /**
     * 遍历树
     *
     * @param tree      欲遍历的树
     * @param predicate 遍历过程中欲进行的处理，
     *                  第一个参数是节点
     *                  返回值true表示继续遍历，false表示中断遍历
     * @param <T>       树节点类型
     */
    <T> boolean each(IterableTree<T> tree, BiPredicate<T, Integer> predicate);

    /**
     * 遍历树
     *
     * @param tree      欲遍历的树
     * @param predicate 遍历过程中欲进行的处理，
     *                  第一个参数是节点
     *                  返回值true表示继续遍历，false表示中断遍历
     * @param <T>       树节点类型
     */
    default <T> boolean each(IterableTree<T> tree, Predicate<T> predicate) {
        return each(tree, StreamUtils.toBiPredicate(predicate));
    }

    abstract class Abstract implements PredicateIterator {
        @Override
        public <T extends Tree.ChildrenNode<T>> boolean each(T root, BiPredicate<T, Integer> biPredicate) {
            return each(root, biPredicate, ROOT_LEVEL);
        }

        protected abstract <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> biPredicate, int level);

        @Override
        public <T> boolean each(IterableTree<T> tree, BiPredicate<T, Integer> biPredicate) {
            return tree.getRoot().filter(t -> each(tree, t, biPredicate, ROOT_LEVEL)).isPresent();
        }

        protected abstract <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> biPredicate, int level);
    }

    PredicateIterator ASC_MUTEX_SATISFIED = new Abstract() {

        protected <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate, int level) {
            if (!predicate.test(node, level)) {
                List<T> children = node.getChildren();
                if (children != null) {
                    for (T child : children) {
                        if (each(child, predicate, level + 1)) return true;
                    }
                }
                return false;
            }
            return true;
        }

        @Override
        protected <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> predicate, int level) {
            if (!predicate.test(node, level)) {
                List<T> children = tree.findChildren(node);
                if (children != null) {
                    for (T child : children) {
                        if (each(tree, child, predicate, level + 1)) return true;
                    }
                }
                return false;
            }
            return true;

        }
    };

    PredicateIterator DESC_MUTEX_SATISFIED = new Abstract() {

        protected <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate, int level) {
            List<T> children = node.getChildren();
            if (children != null) {
                for (T child : children) {
                    if (each(child, predicate, level + 1)) return true;
                }
            }
            return predicate.test(node, level);
        }

        @Override
        protected <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> predicate, int level) {
            List<T> children = tree.findChildren(node);
            if (children != null) {
                for (T child : children) {
                    if (each(tree, child, predicate, level + 1)) return true;
                }
            }
            return predicate.test(node, level);
        }
    };

    PredicateIterator ASC_MUTEX_GREEDY = new Abstract() {

        protected <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate, int level) {
            if (!predicate.test(node, level)) {
                boolean isChildrenFound = false;
                List<T> children = node.getChildren();
                if (children != null) {
                    for (T child : children) {
                        if (each(child, predicate, level + 1)) isChildrenFound = true;
                    }
                }
                return isChildrenFound;
            }
            return true;
        }


        @Override
        protected <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> predicate, int level) {
            if (!predicate.test(node, level)) {
                boolean isChildrenFound = false;
                List<T> children = tree.findChildren(node);
                if (children != null) {
                    for (T child : children) {
                        if (each(tree, child, predicate, level + 1)) isChildrenFound = true;
                    }
                }
                return isChildrenFound;
            }
            return true;

        }
    };

    PredicateIterator DESC_MUTEX_GREEDY = new Abstract() {

        protected <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate, int level) {
            boolean isChildrenFound = false;
            List<T> children = node.getChildren();
            if (children != null) {
                for (T child : children) {
                    if (each(child, predicate, level + 1)) isChildrenFound = true;
                }
            }
            return !isChildrenFound && predicate.test(node, level);
        }

        @Override
        protected <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> predicate, int level) {
            boolean isChildrenFound = false;
            List<T> children = tree.findChildren(node);
            if (children != null) {
                for (T child : children) {
                    if (each(tree, child, predicate, level + 1)) isChildrenFound = true;
                }
            }
            return !isChildrenFound && predicate.test(node, level);

        }
    };

    PredicateIterator ASC_PROGRESSIVE_GREEDY = new Abstract() {

        protected <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate, int level) {
            if (predicate.test(node, level)) {
                List<T> children = node.getChildren();
                if (children != null) {
                    for (T child : children) {
                        each(child, predicate, level + 1);
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        protected <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> predicate, int level) {
            if (predicate.test(node, level)) {
                List<T> children = tree.findChildren(node);
                if (children != null) {
                    for (T child : children) {
                        each(tree, child, predicate, level + 1);
                    }
                }
                return true;
            }
            return false;
        }
    };

    PredicateIterator DESC_PROGRESSIVE_GREEDY = new Abstract() {

        protected <T extends Tree.ChildrenNode<T>> boolean each(T node, BiPredicate<T, Integer> predicate, int level) {
            List<T> children = node.getChildren();
            if (children != null) {
                for (T child : children) {
                    each(child, predicate, level + 1);
                }
            }
            return predicate.test(node, level);
        }

        @Override
        protected <T> boolean each(IterableTree<T> tree, T node, BiPredicate<T, Integer> predicate, int level) {
            List<T> children = tree.findChildren(node);
            if (children != null) {
                for (T child : children) {
                    each(tree, child, predicate, level + 1);
                }
            }
            return predicate.test(node, level);
        }
    };


}
