package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnErrorMessage<T> implements ErrorMessage<T> {
    private Integer columnIndex;
    private Integer[] rowIndexes;
    private T value;
    private String errorMessage;
}
