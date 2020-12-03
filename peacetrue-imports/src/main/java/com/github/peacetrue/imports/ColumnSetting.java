package com.github.peacetrue.imports;

import java.util.function.Function;

/**
 * @author xiayx
 */
public interface ColumnSetting<T> {

    Function<String, T> getParser();

}
