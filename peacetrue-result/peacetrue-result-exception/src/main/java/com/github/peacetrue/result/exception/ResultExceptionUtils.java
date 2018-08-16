package com.github.peacetrue.result.exception;

import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.MessageCapable;

/**
 * the util class for result exception
 *
 * @author xiayx
 */
public abstract class ResultExceptionUtils {

    /**
     * convert something to {@link ResultException}
     *
     * @param something something implements {@link CodeCapable} and {@link MessageCapable}
     * @param <T>       a type implements {@link CodeCapable} and {@link MessageCapable}
     * @return the result exception
     */
    public static <T extends CodeCapable & MessageCapable> ResultException convert(T something) {
        return new ResultException(something.getCode(), something.getMessage());
    }

    /**
     * convert something to {@link ResultException} and throw it
     *
     * @param something something implements {@link CodeCapable} and {@link MessageCapable}
     * @param <T>       a type implements {@link CodeCapable} and {@link MessageCapable}
     */
    public static <T extends CodeCapable & MessageCapable> void throwResultException(T something) {
        throw convert(something);
    }


}