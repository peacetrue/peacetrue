package com.github.peacetrue.imports;

import java.util.List;

/**
 * @author xiayx
 */
public interface ImportsResult<T> {

    List<String[]> getOriginalRecords();

    List<RowNumberWrapper<T>> getParsedRecords();

    List<RowNumberWrapper<T>> getCheckedRecords();

    List<RowNumberWrapper<T>> getSavedRecords();

    List<ErrorMessage> getErrorMessages();

}
