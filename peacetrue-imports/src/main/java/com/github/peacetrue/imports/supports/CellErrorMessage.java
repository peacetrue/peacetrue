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
public class CellErrorMessage<T> implements ErrorMessage<T> {
    private int rowIndex;
    private int columnIndex;
    private T value;
    private String errorMessage;
}
