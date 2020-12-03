package com.github.peacetrue.tree;

import com.github.peacetrue.tree.extractor.*;
import com.github.peacetrue.tree.iterate.ConsumerIterator;
import com.github.peacetrue.tree.iterate.PredicateIterator;
import com.github.peacetrue.tree.node.Material;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @param <N> node
 * @param <M> material
 * @author xiayx
 */
public abstract class VirtualTree<N extends Material<M> & Tree.ChildrenNode<N>, M> implements Tree<M> {

    /** the root of a tree */
    protected N root;
    protected boolean isVirtualRoot = true;

    protected VirtualTree() {
        initRoot();
        Objects.requireNonNull(root);
    }

    protected void initRoot() {
        this.root = initNode(null);
    }

    protected abstract N initNode(M node);

    @Override
    public Optional<M> getRoot() {
        if (isVirtualRoot) {
            if (root.getChildren().size() == 1) {
                return Optional.of(root.getChildren().get(0).getMaterial());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.of(root.getMaterial());
        }
    }

    protected boolean isVirtualNode(M node) {
        return isVirtualRoot && equals(node, root.getMaterial());
    }

    protected boolean equals(N one, N another) {
        return equals(one.getMaterial(), another.getMaterial());
    }

    protected boolean equals(M one, M another) {
        return one.equals(another);
    }

    protected Optional<N> find(M node) {
        return Extractor.extractOne(PredicateIterator.ASC_MUTEX_SATISFIED, root, n -> equals(n.getMaterial(), node));
    }

    public boolean contains(M node) {
        return find(node).filter(n -> !isVirtualNode(n.getMaterial())).isPresent();
    }

    protected abstract boolean isElderOf(M parent, M child);

    /** include self, any match is ok */
    protected boolean isElderOf(M parent, Collection<M> children) {
        return children.stream().anyMatch(node -> isElderOf(parent, node));
    }

    protected boolean isElderOf(N parent, N child) {
        return isElderOf(parent.getMaterial(), child.getMaterial());
    }

    @Override
    public List<M> findParents(M node) {
        return _findParents(node).stream()
                .filter(item -> !isVirtualNode(item.getMaterial()))
                .map(Material::getMaterial).collect(Collectors.toList());
    }

    protected Set<N> _findParents(M node) {
        return Extractor.extractSome(PredicateIterator.ASC_PROGRESSIVE_GREEDY, root,
                new PredicateCollectionExtractor<>(item -> isElderOf(item.getMaterial(), node), new LinkedHashSet<>())
        );
    }

    @Override
    public Optional<M> findParent(M node) {
        return _findParent(node)
                .filter(item -> !isVirtualNode(item.getMaterial()))
                .map(Material::getMaterial);
    }

    protected Optional<N> _findParent(M node) {
        return Extractor.extractOne(PredicateIterator.DESC_MUTEX_SATISFIED, root, item -> isElderOf(item.getMaterial(), node));
    }

    protected Set<N> _findChildren(M node) {
        return Extractor.extractSome(PredicateIterator.ASC_MUTEX_GREEDY, root,
                new PredicateCollectionExtractor<>(item -> isElderOf(node, item.getMaterial()), new LinkedHashSet<>())
        );
    }

    @Override
    public List<M> findChildren(M node) {
        return _findChildren(node).stream().map(Material::getMaterial).collect(Collectors.toList());
    }

    @Override
    public List<M> findYounger(M node) {
        return Extractor.extractSome(ConsumerIterator.ASC, root,
                new FilterConsumerCollectionExtractor<>(item -> isElderOf(node, item.getMaterial()), new LinkedHashSet<>())
        ).stream().map(Material::getMaterial).collect(Collectors.toList());
    }


    @Override
    public List<M> getAllNodes() {
        List<N> some = Extractor.extractSome(ConsumerIterator.ASC, root, ConsumerCollectionExtractor.linkedList());
        if (isVirtualRoot) some.remove(root);
        return some.stream().map(Material::getMaterial).collect(Collectors.toList());
    }

    @Override
    public void addNode(M node) {
        if (equals(node, root.getMaterial())) {
            this.isVirtualRoot = false;
        } else {
            if (contains(node)) {
                //this node is already exist
                throw new IllegalArgumentException("the special node " + node + "is already exist");
            } else {
                N _node = initNode(node);
                _findParent(node).ifPresent(n -> addNode(n, _node));
            }
        }
    }

    protected void addNode(N parent, N child) {
        List<N> younger = parent.getChildren().stream()
                .filter(node -> isElderOf(child, node)).collect(Collectors.toList());
        parent.getChildren().removeAll(younger);
        parent.getChildren().add(child);
        child.getChildren().addAll(younger);
    }


    @Override
    public boolean removeNode(M node) {
        if (equals(node, root.getMaterial())) {
            if (!isVirtualRoot) {
                isVirtualRoot = true;
                return true;
            }
            return false;
        }

        Optional<N> optional = Extractor.extractOne(PredicateIterator.ASC_MUTEX_SATISFIED, root, _node -> equals(_node.getMaterial(), node));
        if (!optional.isPresent()) return false;

        N _node = optional.get();
        _findParent(node).ifPresent(parent -> removeNode(parent, _node));
        return true;
    }

    protected void removeNode(N parent, N child) {
        int index = parent.getChildren().indexOf(child);
        parent.getChildren().remove(index);
        parent.getChildren().addAll(index, child.getChildren());//TODO maybe remove the exist child
    }

    @Override
    public Tree<M> subtree(M node) {
        return null;
    }

    @Override
    public Tree<M> localTree(Collection<M> nodes) {
//        Assertions.notEmpty(nodes, "nodes must not be empty");
        VirtualTree<N, M> virtualTree = initTree();
        Extractor.extractSome(PredicateIterator.ASC_PROGRESSIVE_GREEDY, root,
                new PredicateCollectionExtractor<>(node -> isElderOf(node.getMaterial(), nodes), new LinkedHashSet<>())
        ).forEach(node -> virtualTree.addNode(node.getMaterial()));
        //the above process make isVirtualRoot=false
        if (this.isVirtualRoot || !nodes.contains(root.getMaterial())) virtualTree.isVirtualRoot = true;
        return virtualTree;
    }

    @SuppressWarnings("unchecked")
    protected VirtualTree<N, M> initTree() {
        return BeanUtils.instantiateClass(this.getClass());
    }

    public void reset() {
        this.root.getChildren().clear();
        this.isVirtualRoot = true;
    }


    @Override
    public String toString() {
        return Extractor.extractAny(ConsumerIterator.ASC, root, new StringExtractor<>()).toString();
    }


}
