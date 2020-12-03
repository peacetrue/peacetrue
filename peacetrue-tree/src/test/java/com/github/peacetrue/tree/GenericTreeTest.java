package com.github.peacetrue.tree;

import com.github.peacetrue.tree.extractor.Extractor;
import com.github.peacetrue.tree.extractor.StringExtractor;
import com.github.peacetrue.tree.iterate.ConsumerIterator;
import com.github.peacetrue.util.AssertUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 泛化树测试，以类的树形结构为示例
 *
 * @author xiayx
 */
public class GenericTreeTest {
    private List<Class> classes = Arrays.asList(
            Object.class,
            Throwable.class,
            Error.class,
            LinkageError.class,
            ThreadDeath.class,
            AssertionError.class,
            VirtualMachineError.class,
            Exception.class,
            CloneNotSupportedException.class,
            ReflectiveOperationException.class,
            RuntimeException.class,
            IndexOutOfBoundsException.class,
            ArithmeticException.class,
            ClassCastException.class,
            NullPointerException.class,
            IllegalArgumentException.class,
            InterruptedException.class
    );

    private GenericTree<Class> tree = new GenericTree<>(
            node -> node.equals(Object.class),
            (parent, child) -> parent.equals(child.getSuperclass()),
            classes);

    @Test
    public void showTree() throws Exception {
        System.out.println(Extractor.extractAny(ConsumerIterator.ASC, tree, new StringExtractor<>("-")));
    }

    @Test
    public void checkTree() throws Exception {
        List<Class> classes = Arrays.asList(
                Object.class,
                Exception.class
        );
        Throwable throwable = AssertUtils.assertException(() -> new GenericTree<>(
                node -> node.equals(Object.class),
                (parent, child) -> parent.equals(child.getSuperclass()),
                classes), "Exception.class'parent Throwable.class not exists");
        System.out.println(throwable);
    }

    @Test
    public void getRoot() throws Exception {
        Assertions.assertTrue(tree.getRoot().isPresent());
        Assertions.assertEquals(tree.getRoot().get(), Object.class);
    }

    @Test
    public void contains() throws Exception {
        classes.forEach(aClass -> Assertions.assertTrue(tree.contains(aClass)));
    }

    public static List<Class> getSuperClasses(Class clazz) {
        List<Class> classList = new ArrayList<>();
        while ((clazz = clazz.getSuperclass()) != null) {
            classList.add(clazz);
        }
        Collections.reverse(classList);
        return classList;
    }

    @Test
    public void findParent() throws Exception {
        classes.forEach(aClass -> {
            Assertions.assertEquals(aClass.getSuperclass(), tree.findParent(aClass).orElse(null));
        });
    }

    @Test
    public void findParents() throws Exception {
        classes.forEach(aClass -> {
            Assertions.assertEquals(getSuperClasses(aClass), tree.findParents(aClass));
        });
    }

    @Test
    public void findSameParents() throws Exception {
        List<Class> sameParents = tree.findSameParents(Arrays.asList(NullPointerException.class, CloneNotSupportedException.class));
        Assertions.assertEquals(Arrays.asList(Object.class, Throwable.class, Exception.class), sameParents);
    }

    @Test
    public void findSameParent() throws Exception {
        Optional<Class> parent = tree.findSameParent(Arrays.asList(NullPointerException.class, CloneNotSupportedException.class));
        Assertions.assertTrue(parent.isPresent());
        Assertions.assertEquals(parent.get(), Exception.class);
    }

    @Test
    public void findChildren() throws Exception {
        Assertions.assertEquals(Arrays.asList(
                IndexOutOfBoundsException.class,
                ArithmeticException.class,
                ClassCastException.class,
                NullPointerException.class,
                IllegalArgumentException.class),
                tree.findChildren(RuntimeException.class)
        );
    }

    @Test
    public void findYounger() throws Exception {
        Assertions.assertEquals(Arrays.asList(
                CloneNotSupportedException.class,
                ReflectiveOperationException.class,
                RuntimeException.class,
                IndexOutOfBoundsException.class,
                ArithmeticException.class,
                ClassCastException.class,
                NullPointerException.class,
                IllegalArgumentException.class,
                InterruptedException.class),
                tree.findYounger(Exception.class)
        );

    }

    @Test
    public void getAllNodes() throws Exception {
        Assertions.assertEquals(classes, new ArrayList<>(tree.getAllNodes()));
    }

    @Test
    public void addNode() throws Exception {
        GenericTree<Class> tree = new GenericTree<>(
                node -> node.equals(Object.class),
                (parent, child) -> parent.equals(child.getSuperclass())
        );
        AssertUtils.assertException(() -> tree.addNode(String.class));
        tree.addNode(Object.class);
        AssertUtils.assertException(() -> tree.addNode(Integer.class));
        tree.addNode(Number.class);
        tree.addNode(Integer.class);
        tree.addNode(String.class);
        System.out.println(tree);
    }

    @Test
    public void removeNode() throws Exception {
        GenericTree<Class> tree = new GenericTree<>(
                node -> node.equals(Object.class),
                (parent, child) -> parent.equals(child.getSuperclass()),
                classes
        );
        Assertions.assertFalse(tree.removeNode(Long.class));
        Assertions.assertTrue(tree.removeNode(NullPointerException.class));
        Assertions.assertTrue(tree.removeNode(RuntimeException.class));
        Assertions.assertFalse(tree.removeNode(IllegalArgumentException.class));
    }

    @Test
    public void subtree() throws Exception {
        AssertUtils.assertException(() -> tree.subtree(Long.class));
        Tree<Class> subtree = tree.subtree(Exception.class);
        Assertions.assertEquals(Arrays.asList(
                Exception.class,
                CloneNotSupportedException.class,
                ReflectiveOperationException.class,
                RuntimeException.class,
                IndexOutOfBoundsException.class,
                ArithmeticException.class,
                ClassCastException.class,
                NullPointerException.class,
                IllegalArgumentException.class,
                InterruptedException.class),
                new ArrayList<>(subtree.getAllNodes()));
        System.out.println(subtree);
    }

    @Test
    public void localTree() throws Exception {
        AssertUtils.assertException(() -> tree.localTree(Collections.singleton(Long.class)));
        Tree<Class> localTree = tree.localTree(Arrays.asList(Error.class, IndexOutOfBoundsException.class));
        Assertions.assertEquals(Arrays.asList(
                Object.class,
                Throwable.class,
                Error.class,
                Exception.class,
                RuntimeException.class,
                IndexOutOfBoundsException.class
        ), new ArrayList<>(localTree.getAllNodes()));
        System.out.println(localTree);
    }

}
