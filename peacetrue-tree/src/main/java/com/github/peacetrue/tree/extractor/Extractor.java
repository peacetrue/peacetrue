package com.github.peacetrue.tree.extractor;

import com.github.peacetrue.tree.Tree;
import com.github.peacetrue.tree.iterate.ConsumerIterator;
import com.github.peacetrue.tree.iterate.IterableTree;
import com.github.peacetrue.tree.iterate.PredicateIterator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * extractor, try to extract something
 *
 * @author xiayx
 */
public interface Extractor<T> {

    /** get the extract, must not be null, if null use {@link Optional} instead */
    T getExtract();

    /*---------不可中断的遍历-------------*/
    interface BiConsumerExtractor<N, T> extends BiConsumer<N, Integer>, Extractor<T> {}

    interface ConsumerExtractor<N, T> extends Consumer<N>, Extractor<T> {}

    static <N extends Tree.ChildrenNode<N>, T> T extractAny(ConsumerIterator iterator, N root, BiConsumerExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N extends Tree.ChildrenNode<N>, T> T extractAny(ConsumerIterator iterator, N root, ConsumerExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N extends Tree.ChildrenNode<N>, T extends Collection<N>> T extractSome(ConsumerIterator iterator, N root, BiConsumerExtractor<N, T> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N extends Tree.ChildrenNode<N>, T extends Collection<N>> T extractSome(ConsumerIterator iterator, N root, ConsumerExtractor<N, T> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N extends Tree.ChildrenNode<N>> List<N> extractSome(ConsumerIterator iterator, N root, Predicate<N> predicate) {
        return extractSome(iterator, root, FilterConsumerCollectionExtractor.linkedList(predicate));
    }

    static <N extends Tree.ChildrenNode<N>> List<N> extractAll(ConsumerIterator iterator, N root) {
        return extractSome(iterator, root, ConsumerCollectionExtractor.linkedList());
    }


    static <N, T> T extractAny(ConsumerIterator iterator, IterableTree<N> root, BiConsumerExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N, T> T extractAny(ConsumerIterator iterator, IterableTree<N> root, ConsumerExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N, T extends Collection<N>> T extractSome(ConsumerIterator iterator, IterableTree<N> root, BiConsumerExtractor<N, T> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N, T extends Collection<N>> T extractSome(ConsumerIterator iterator, IterableTree<N> root, ConsumerExtractor<N, T> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N> List<N> extractSome(ConsumerIterator iterator, IterableTree<N> root, Predicate<N> predicate) {
        return extractSome(iterator, root, FilterConsumerCollectionExtractor.linkedList(predicate));
    }

    static <N> List<N> extractAll(ConsumerIterator iterator, IterableTree<N> root) {
        return extractSome(iterator, root, ConsumerCollectionExtractor.linkedList());
    }


    /*---------可中断的遍历-------------*/
    interface BiPredicateExtractor<N, T> extends BiPredicate<N, Integer>, Extractor<T> {}

    interface PredicateExtractor<N, T> extends Predicate<N>, Extractor<T> {}

    static <N extends Tree.ChildrenNode<N>, T> T extractAny(PredicateIterator iterator, N root, BiPredicateExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N extends Tree.ChildrenNode<N>, T> T extractAny(PredicateIterator iterator, N root, PredicateExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N extends Tree.ChildrenNode<N>, T extends Collection<N>> T extractSome(PredicateIterator iterator, N root, PredicateExtractor<N, T> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N extends Tree.ChildrenNode<N>> List<N> extractSome(PredicateIterator iterator, N root, Predicate<N> predicate) {
        return extractSome(iterator, root, new PredicateCollectionExtractor<>(predicate, new LinkedList<>()));
    }

    static <N extends Tree.ChildrenNode<N>> Optional<N> extractOne(PredicateIterator iterator, N root, PredicateExtractor<N, Optional<N>> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N extends Tree.ChildrenNode<N>> Optional<N> extractOne(PredicateIterator iterator, N root, Predicate<N> predicate) {
        return extractOne(iterator, root, new PredicateOneExtractor<>(predicate));
    }

    static <N, T> T extractAny(PredicateIterator iterator, IterableTree<N> root, BiPredicateExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N, T> T extractAny(PredicateIterator iterator, IterableTree<N> root, PredicateExtractor<N, T> extractor) {
        iterator.each(root, extractor);
        return extractor.getExtract();
    }

    static <N, T extends Collection<N>> T extractSome(PredicateIterator iterator, IterableTree<N> root, PredicateExtractor<N, T> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N> List<N> extractSome(PredicateIterator iterator, IterableTree<N> root, Predicate<N> predicate) {
        return extractSome(iterator, root, new PredicateCollectionExtractor<>(predicate, new LinkedList<>()));
    }

    static <N> Optional<N> extractOne(PredicateIterator iterator, IterableTree<N> root, PredicateExtractor<N, Optional<N>> extractor) {
        return extractAny(iterator, root, extractor);
    }

    static <N> Optional<N> extractOne(PredicateIterator iterator, IterableTree<N> root, Predicate<N> predicate) {
        return extractOne(iterator, root, new PredicateOneExtractor<>(predicate));
    }

}
