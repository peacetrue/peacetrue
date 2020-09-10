package com.github.peacetrue.spring.data.relational.core.query;

import org.springframework.data.relational.core.query.Query;

import java.util.function.Supplier;

/**
 * util class for {@link Query}
 *
 * @author : xiayx
 * @since : 2020-05-22 13:06
 **/
public abstract class QueryUtils {

    /** protected to be extends but avoid instance */
    protected QueryUtils() {
    }

    /** id Query */
    public static Query id(Supplier<?> idSupplier) {
        return nullableId(idSupplier);
    }

    /** when null id use empty Query */
    public static Query nullableId(Supplier<?> idSupplier) {
        return Query.query(CriteriaUtils.nullableId(idSupplier));
    }

}
