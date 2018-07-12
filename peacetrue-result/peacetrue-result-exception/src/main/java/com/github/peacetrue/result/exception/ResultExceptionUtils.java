package com.github.peacetrue.result.exception;

import com.github.peacetrue.core.Code;
import com.github.peacetrue.core.Message;

/**
 * the util class for result exception
 *
 * @author xiayx
 */
public abstract class ResultExceptionUtils {

    /**
     * convert something to {@link ResultException}
     *
     * @param something something implements {@link Code} and {@link Message}
     * @param <T>       a type implements {@link Code} and {@link Message}
     * @return the result exception
     */
    public static <T extends Code & Message> ResultException convert(T something) {
        return new ResultException(something.getCode(), something.getMessage());
    }

    /**
     * convert something to {@link ResultException} and throw it
     *
     * @param something something implements {@link Code} and {@link Message}
     * @param <T>       a type implements {@link Code} and {@link Message}
     */
    public static <T extends Code & Message> void throwResultException(T something) {
        throw convert(something);
    }


}