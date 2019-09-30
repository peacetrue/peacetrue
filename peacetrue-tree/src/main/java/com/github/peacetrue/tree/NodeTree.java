package com.github.peacetrue.tree;

import com.github.peacetrue.tree.extractor.Extractor;
import com.github.peacetrue.tree.extractor.StringExtractor;
import com.github.peacetrue.tree.iterate.ConsumerIterator;
import com.github.peacetrue.tree.iterate.PredicateIterator;
import com.github.peacetrue.util.CloneUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 节点树，节点是一个{@link Tree.Node}类型
 *
 * @author xiayx
 * @see GenericTree
 */
public class NodeTree<N extends Tree.Node<N> & Tree.ParentNodeAware<N>> implements Tree<N> {

    protected N root;

    /** 构造一颗空树，可随后通过{@link #setRoot(Node)}设置根节点 */
    public NodeTree() {
    }

    /** 构造一颗有效树 */
    public NodeTree(N root) {
        setRoot(root);
    }

    public void setRoot(N root) {
        if (!isRoot(root)) throw new IllegalArgumentException(root + " not a valid root node");
        this.root = root;
    }

    public boolean isRoot(N node) {
        return node.getParent() == null || node.getParent() == node;
    }

    @Override
    public Optional<N> getRoot() {
        return Optional.ofNullable(root);
    }

    private Optional<N> findNode(N node) {
        return Extractor.extractOne(PredicateIterator.ASC_MUTEX_SATISFIED, root, node::equals);
    }

    private N findRequiredNode(N node) {
        return findNode(node).orElseThrow(() -> Tree.notExistException(node));
    }

    @Override
    public boolean contains(N node) {
        return findNode(node).isPresent();
    }

    @Override
    public Optional<N> findParent(N node) {
        return Optional.ofNullable(findRequiredNode(node).getParent());
    }

    public List<N> findParents(N node) {
        return findParentsInternal(findRequiredNode(node));
    }

    private List<N> findParentsInternal(N node) {
        List<N> parents = new LinkedList<>();
        while (!isRoot(node)) {
            node = node.getParent();
            parents.add(node);
        }
        Collections.reverse(parents);
        return parents;
    }

    @Override
    public List<N> findChildren(N node) {
        return findRequiredNode(node).getChildren();
    }

    @Override
    public List<N> findYounger(N node) {
        return Extractor.extractAll(ConsumerIterator.ASC, findRequiredNode(node));
    }

    @Override
    public List<N> getAllNodes() {
        return Extractor.extractAll(ConsumerIterator.ASC, root);
    }

    @Override
    public void addNode(N node) {
        if (root == null) {
            if (isRoot(node)) {
                root = node;
                return;
            } else {
                throw new IllegalStateException("must add root node first for empty tree");
            }
        }

        if (node.getParent() == null) {
            throw new IllegalStateException("the parent of node " + node + " must not be null");
        }

        Optional<N> optional = findNode(node.getParent());
        if (!optional.isPresent()) {
            throw new IllegalStateException("the parent " + node.getParent() + " of node " + node + " must exists in tree");
        }

        List<N> children = optional.get().getChildren();
        if (children.contains(node)) {
            Tree.throwExistException(node);
        } else {
            children.add(node);
        }
    }

    @Override
    public boolean removeNode(N node) {
        node = findRequiredNode(node);
        if (isRoot(node)) {
            root = null;
        } else {
            node.getParent().getChildren().remove(node);
        }
        return true;
    }

    @Override
    public Tree<N> subtree(N node) {
        return new NodeTree<>(findRequiredNode(node));
    }

    /**
     * 构造本地树
     * <p>
     * 实现逻辑：
     * <ul>
     * <li>获取节点路径</li>
     * <li>克隆节点路径</li>
     * <li>去除重复节点</li>
     * <li>关联节点路径</li>
     * <li>找到根节点构造树</li>
     * </ul>
     */
    @Override
    public Tree<N> localTree(Collection<N> nodes) {
        //缓存已经克隆过的节点，已经克隆过的不重复克隆
        Map<N, N> clones = new HashMap<>();
        List<List<N>> paths = nodes.stream()
                .map(this::findRequiredNode)
                .map(this::findParentsAndSelf)
                .map(ns -> clone(ns, clones))
                .peek(this::link)
                .collect(Collectors.toList());
        return new NodeTree<>(paths.get(0).get(0));
    }

    /** 查找父辈节点，并在末尾加上自己 */
    private List<N> findParentsAndSelf(N node) {
        List<N> parents = this.findParentsInternal(node);
        parents.add(node);
        return parents;
    }

    /** 克隆节点集合 */
    private List<N> clone(List<N> nodes, Map<N, N> clones) {
        return nodes.stream().map(n -> {
            N clone = clones.get(n);
            if (clone == null) {
                clone = CloneUtils.clone(n);
                clone.setParent(null);
                clone.getChildren().clear();
                clones.put(n, clone);
            }
            return clone;
        }).collect(Collectors.toList());
    }

    /** 将节点路径（从根节点到叶子节点）关联上 */
    private void link(List<N> nodes) {
        for (int i = 1; i < nodes.size(); i++) {
            link(nodes.get(i - 1), nodes.get(i));
        }
    }

    /** 将父子节点关联上 */
    private void link(N parent, N child) {
        if (child.getParent() == null) child.setParent(parent);
        if (!parent.getChildren().contains(child)) parent.getChildren().add(child);
    }

    @Override
    public String toString() {
        return Extractor.extractAny(ConsumerIterator.ASC, root, new StringExtractor<>()).toString();
    }
}
