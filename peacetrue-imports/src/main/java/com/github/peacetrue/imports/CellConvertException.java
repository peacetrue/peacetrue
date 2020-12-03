package com.github.peacetrue.imports;

import lombok.Getter;

/**
 * @author xiayx
 */
@Getter
public class CellConvertException extends Exception {

    private int index;
    private String value;
    private String message;

    public CellConvertException(int index, String value, String message) {
        super(String.format("the value [%s] at index [%s] can't be converted", value, index));
        this.index = index;
        this.value = value;
        this.message = message;
    }
}
