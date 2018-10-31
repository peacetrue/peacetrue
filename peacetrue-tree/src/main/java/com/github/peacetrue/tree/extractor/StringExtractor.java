package com.github.peacetrue.tree.extractor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <T>
 */
public class StringExtractor<T> implements Extractor.BiConsumerExtractor<T, StringBuilder> {

    private StringBuilder target = new StringBuilder();
    private String prefix;
    private String suffix;

    public StringExtractor() {
        this("  ");
    }

    public StringExtractor(String prefix) {
        this(prefix, "\n");
    }

    public StringExtractor(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public void accept(T node, Integer level) {
        target.append(Stream.generate(() -> prefix).limit(level).collect(Collectors.joining())).append(node).append(suffix);
    }

    public StringBuilder getExtract() {
        return target;
    }

}