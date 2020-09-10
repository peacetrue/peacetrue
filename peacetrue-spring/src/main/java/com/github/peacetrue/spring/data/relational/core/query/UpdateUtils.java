package com.github.peacetrue.spring.data.relational.core.query;

import com.github.peacetrue.spring.util.BeanUtils;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * util class for {@link Update}
 *
 * @author : xiayx
 * @since : 2020-05-22 19:21
 **/
public abstract class UpdateUtils {

    /** protected to be extends but avoid instance */
    protected UpdateUtils() {
    }

    /** update some property of example, ignored empty value and id property */
    public static Update selectiveUpdateFromExample(Object example) {
        return selectiveUpdateFromExample(example, BeanUtils.NOT_EMPTY_PROPERTY_VALUE
                .and((key, value) -> !key.equals(CriteriaUtils.ID_PROPERTY_NAME)));
    }

    /** update some property of example */
    public static Update selectiveUpdateFromExample(Object example, BiPredicate<String, Object> includeProperty) {
        return Update.from(BeanUtils.map(example, includeProperty)
                .entrySet().stream()
                .collect(Collectors.toMap(entry -> SqlIdentifier.unquoted(entry.getKey()), Map.Entry::getValue)));
    }
}
