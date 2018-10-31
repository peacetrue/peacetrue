package com.github.peacetrue.tree;

import com.github.peacetrue.tree.extractor.Extractor;
import com.github.peacetrue.tree.iterate.IterableTree;
import com.github.peacetrue.tree.iterate.PredicateIterator;

import java.util.*;
import java.util.function.Predicate;

/**
 * 树结构
 * <p>
 * 基本要求：
 * <ul>
 * <li>必须有且仅有一个根节点</li>
 * <li>树中的节点必须是唯一</li>
 * <li>根节点没有父节点，其他节点有且仅有一个父节点</li>
 * <li>每个节点可以有多个子节点</li>
 * </ul>
 * java的类继承结构是一颗典型的树，例如：
 * <pre>
 * class java.lang.Object
 * -class java.lang.Throwable
 * --class java.lang.Error
 * ---class java.lang.LinkageError
 * ---class java.lang.ThreadDeath
 * ---class java.lang.AssertionError
 * ---class java.lang.VirtualMachineError
 * --class java.lang.Exception
 * ---class java.lang.CloneNotSupportedException
 * ---class java.lang.ReflectiveOperationException
 * ---class java.lang.RuntimeException
 * ----class java.lang.IndexOutOfBoundsException
 * ----class java.lang.ArithmeticException
 * ----class java.lang.ClassCastException
 * ----class java.lang.NullPointerException
 * ----class java.lang.IllegalArgumentException
 * ---class java.lang.InterruptedException
 * </pre>
 * 方法说明会针对此示例进行举例。
 * <p>
 * 必要的方法：
 * <ul>
 * <li>{@link #getRoot()}和{@link #findChildren(Object)}，这两个方法可用于遍历整颗树</li>
 * <li>{@link #findParents(Object)}，可用于获取节点路径，其依赖于{@link #findParent(Object)}</li>
 * <li>{@link #localTree(Collection)}，根据权限过滤菜单后重新构造可显示的菜单非常有用</li>
 * </ul>
 * <p>
 * 注意事项：
 * <ul>
 * <li>写入操作的节点必须不存在于树中，读取操作的节点必须存在于树中</li>
 * <li>树中是否可以临时存储树外的节点：不可以，避免将问题复杂化。</li>
 * </ul>
 *
 * @author xiayx
 */
public interface Tree<T> extends IterableTree<T> {

    /**
     * 获取根节点。
     * 空树不存在根节点。
     * <p>
     * 示例中：根节点为Object
     */
    Optional<T> getRoot();

    /** 查找符合条件的节点 */
    default Optional<T> findNode(Predicate<T> predicate) {
        return Extractor.extractOne(PredicateIterator.ASC_MUTEX_SATISFIED, this, predicate);
    }

    /** 查找符合条件的节点集合 */
    default Collection<T> findNodes(Predicate<T> predicate) {
        return Extractor.extractSome(PredicateIterator.ASC_MUTEX_SATISFIED, this, predicate);
    }

    /**
     * 树中是否包含指定节点。
     * 通过{@link #equals(Object)}方法比较两个节点是否相等
     */
    boolean contains(T node);

    /**
     * 查找父节点。
     * 除了根节点没有父节点，其他节点都有父节点。
     * 指定节点必须存在于树中。
     * <p>
     * 示例中：RuntimeException的父节点是Exception
     */
    Optional<T> findParent(T node);

    /**
     * 查找父辈节点，按根节点->子节点的顺序排列。
     * 指定节点必须存在于树中。
     * <p>
     * 示例中：RuntimeException的父辈节点是[Object,Throwable,Exception]
     */
    default List<T> findParents(T node) {
        List<T> parents = new LinkedList<>();
        Optional<T> parent = Optional.of(node);
        while ((parent = findParent(parent.get())).isPresent()) {
            parents.add(parent.get());
        }
        Collections.reverse(parents);
        return parents;
    }

    /**
     * 查找子节点。
     * 指定节点必须存在于树中。
     * <p>
     * 示例中：Throwable的子节点为[Error,Exception]
     */
    List<T> findChildren(T node);

    /**
     * 查找所有子辈节点。
     * 不要求指定节点必须存在于树中。
     * <p>
     * 示例中：Throwable的子辈节点为：
     * <pre>
     * --class java.lang.Error
     * ---class java.lang.LinkageError
     * ---class java.lang.ThreadDeath
     * ---class java.lang.AssertionError
     * ---class java.lang.VirtualMachineError
     * --class java.lang.Exception
     * ---class java.lang.CloneNotSupportedException
     * ---class java.lang.ReflectiveOperationException
     * ---class java.lang.RuntimeException
     * ----class java.lang.IndexOutOfBoundsException
     * ----class java.lang.ArithmeticException
     * ----class java.lang.ClassCastException
     * ----class java.lang.NullPointerException
     * ----class java.lang.IllegalArgumentException
     * ---class java.lang.InterruptedException
     * </pre>
     */
    List<T> findYounger(T node);

    /** 获取所有节点 */
    Collection<T> getAllNodes();

    /**
     * 添加节点。
     * 指定节点必须不存在于树中且父节点必须存在于树中
     */
    void addNode(T node);

    /**
     * 删除节点，同时删除所有子辈节点。
     * 指定节点必须存在于树中。
     */
    boolean removeNode(T node);

    /**
     * 构造一个以指定节点为根节点子树。
     * 指定节点必须存在于树中。
     * <p>
     * 示例中：以Exception构造的子树为：
     * <pre>
     * --class java.lang.Exception
     * ---class java.lang.CloneNotSupportedException
     * ---class java.lang.ReflectiveOperationException
     * ---class java.lang.RuntimeException
     * ----class java.lang.IndexOutOfBoundsException
     * ----class java.lang.ArithmeticException
     * ----class java.lang.ClassCastException
     * ----class java.lang.NullPointerException
     * ----class java.lang.IllegalArgumentException
     * ---class java.lang.InterruptedException
     * </pre>
     */
    Tree<T> subtree(T node);

    /**
     * 构造一个包含指定节点局部树。
     * 指定节点必须存在于树中。
     * <p>
     * 示例中：以[Error,IndexOutOfBoundsException]构造的局部树为：
     * <pre>
     * class java.lang.Object
     * -class java.lang.Throwable
     * --class java.lang.Error
     * --class java.lang.Exception
     * ----class java.lang.IndexOutOfBoundsException
     * </pre>
     */
    Tree<T> localTree(Collection<T> nodes);

    interface ParentNode<T extends ParentNode<T>> {
        T getParent();
    }

    interface ParentNodeAware<T extends ParentNodeAware<T>> {
        /** 使用{@link NodeTree#localTree(Collection)}时需要依赖此方法 */
        void setParent(T parent);
    }

    interface ChildrenNode<T extends ChildrenNode<T>> {
        List<T> getChildren();
    }

    interface Node<T extends Node<T>> extends ParentNode<T>, ChildrenNode<T> {
        T getParent();
        List<T> getChildren();
    }


    static RuntimeException notExistException(Object node) {
        return new IllegalStateException("the node " + node + " is't exists in tree");
    }

    static RuntimeException existException(Object node) {
        return new IllegalStateException("the node " + node + " is already exists in tree");
    }

    static void throwExistException(Object node) {
        throw existException(node);
    }

    static void throwNotExistException(Object node) {
        throw notExistException(node);
    }
}
