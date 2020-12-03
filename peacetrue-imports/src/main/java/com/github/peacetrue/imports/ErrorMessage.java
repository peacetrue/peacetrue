package com.github.peacetrue.imports;

/**
 * @author xiayx
 */
public interface ErrorMessage<T> {

    T getValue();

    String getErrorMessage();
}
