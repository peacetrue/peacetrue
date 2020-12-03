package com.github.peacetrue.imports;

/**
 * @author xiayx
 */
public interface RowConverter<T> {

    T convert(Object[] cells, ImportsSetting setting) throws CellConvertException;

}
