package com.github.peacetrue.tree;

import com.github.peacetrue.tree.extractor.Extractor;
import com.github.peacetrue.tree.extractor.StringExtractor;
import com.github.peacetrue.tree.generator.TreeNodeBuilder;
import com.github.peacetrue.tree.generator.TreeNodeGenerator;
import com.github.peacetrue.tree.iterate.ConsumerIterator;
import com.github.peacetrue.tree.menu.NodeMenu;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiayx
 */
public class NodeTreeTest {

    TreeNodeGenerator<NodeMenu> generator = new TreeNodeGenerator<>(3, 3, new TreeNodeBuilder<NodeMenu>() {
        private long id = 1L;

        @Override
        public NodeMenu build(NodeMenu parent, int index, int level) {
            return parent == null ? new NodeMenu(id++) : new NodeMenu(parent, id++);
        }
    });
    List<NodeMenu> generate = generator.generate();
    private NodeTree<NodeMenu> tree = new NodeTree<>(generate.get(0));

    @Test
    public void showTree() throws Exception {
        System.out.println(Extractor.extractAny(ConsumerIterator.ASC, tree, new StringExtractor<>("-")));
    }

    @Test
    public void getRoot() throws Exception {

    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void findParent() throws Exception {
    }

    @Test
    public void findParents() throws Exception {
    }

    @Test
    public void findChildren() throws Exception {
    }

    @Test
    public void findYounger() throws Exception {
    }

    @Test
    public void getAllNodes() throws Exception {
    }

    @Test
    public void addNode() throws Exception {
    }

    @Test
    public void removeNode() throws Exception {
    }

    @Test
    public void subtree() throws Exception {
    }

    @Test
    public void localTree() throws Exception {
        System.out.println(tree);
        System.out.println(tree.localTree(Arrays.asList(generate.get(14), generate.get(19))));
    }

}
