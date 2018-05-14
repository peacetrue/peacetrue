package com.github.peacetrue.result.exception.converters;

import javax.annotation.Nullable;

/**
 * arguments as data
 *
 * @author xiayx
 */
public abstract class ArgumentAsDataExceptionConverter<T extends Exception> extends AbstractExceptionConverter<T, Object[]> {

    @Nullable
    @Override
    protected Object[] resolveArguments(T exception, Object[] data) {
        return data;
    }
}
