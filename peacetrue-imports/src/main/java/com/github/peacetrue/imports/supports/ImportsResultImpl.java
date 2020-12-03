package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ErrorMessage;
import com.github.peacetrue.imports.ImportsResult;
import com.github.peacetrue.imports.RowNumberWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportsResultImpl<T> implements ImportsResult<T> {

    private List<String[]> originalRecords = new LinkedList<>();
    private List<RowNumberWrapper<T>> parsedRecords = new LinkedList<>();
    private List<RowNumberWrapper<T>> checkedRecords = new LinkedList<>();
    private List<RowNumberWrapper<T>> savedRecords = new LinkedList<>();
    private List<ErrorMessage> errorMessages = new LinkedList<>();

}
