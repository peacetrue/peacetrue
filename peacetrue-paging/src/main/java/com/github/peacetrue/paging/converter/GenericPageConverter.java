package com.github.peacetrue.paging.converter;

import com.github.peacetrue.paging.PageAttribute;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.spring.util.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * a generic {@link PageConverter}
 *
 * @author xiayx
 */
public class GenericPageConverter implements PageConverter<Object> {

    public static final Map<PageAttribute, String> DEFAULT_SOURCE;
    public static final Map<PageAttribute, String> DEFAULT_TARGET;

    static {
        Map<PageAttribute, String> defaults = Arrays.stream(PageAttribute.values())
                .collect(Collectors.toMap(Function.identity(), Enum::name));
        DEFAULT_SOURCE = Collections.unmodifiableMap(defaults);
        DEFAULT_TARGET = DEFAULT_SOURCE;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<PageAttribute, String> source = DEFAULT_SOURCE;
    private Map<PageAttribute, String> target = DEFAULT_TARGET;

    @Override
    public Map<String, Object> convert(Object page) {
        logger.info("convert source page to target page");
        return Arrays.stream(PageAttribute.values()).collect(
                Collectors.toMap(
                        o -> target.getOrDefault(o, DEFAULT_TARGET.get(o)),
                        o -> BeanUtils.getPropertyValue(page, source.getOrDefault(o, DEFAULT_SOURCE.get(o))),
                        EnumUtils.throwingMerger(),
                        LinkedHashMap::new
                )
        );
    }

    public void setSource(Map<PageAttribute, String> source) {
        this.source = Objects.requireNonNull(source);
    }

    public void setTarget(Map<PageAttribute, String> target) {
        this.target = Objects.requireNonNull(target);
    }
}
