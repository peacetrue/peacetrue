package com.github.peacetrue.spring.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.function.Consumer;

/**
 * util class for {@link DataAccessException}
 *
 * @author : xiayx
 * @since : 2020-05-22 13:54
 **/
public abstract class DataAccessExceptionUtils {

    /** protected to be extends but avoid instance */
    protected DataAccessExceptionUtils() {
    }

    /** write operator must effect one record */
    public static Consumer<Integer> checkUnique() {
        return DataAccessExceptionUtils::checkUnique;
    }

    /** write operator must effect one record */
    public static void checkUnique(Integer size) {
        if (size != 1) throw new IncorrectResultSizeDataAccessException(1, size);
    }

    /** read operator must get one record */
    public static <T> Consumer<T> checkExists() {
        return DataAccessExceptionUtils::checkExists;
    }

    /** read operator must get one record */
    public static <T> void checkExists(T value) {
        if (value == null) throw new IncorrectResultSizeDataAccessException(1, 0);
    }
}
