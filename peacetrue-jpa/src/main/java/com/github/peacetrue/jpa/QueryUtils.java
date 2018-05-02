package com.github.peacetrue.jpa;

import javax.annotation.Nullable;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * a util class for {@link Query}
 *
 * @author xiayx
 */
public abstract class QueryUtils {

    /**
     * similar to {@link Query#getSingleResult()},
     * but avoid {@link NoResultException} or {@link NonUniqueResultException}
     *
     * @param query a query
     * @param <T>   the type of return result
     * @return a single result
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getSingleResult(Query query) {
        List resultList = query.getResultList();
        return resultList.size() == 1 ? (T) resultList.get(0) : null;
    }

}
