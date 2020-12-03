package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.RowNumberWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RowNumberWrapperImpl<T> implements RowNumberWrapper<T> {

    private Integer rowNumber;
    private T row;

    public RowNumberWrapperImpl(T row) {
        this.row = row;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof RowNumberWrapperImpl)) return false;
        return Objects.equals(((RowNumberWrapperImpl<?>) object).getRow(), row);
    }

    @Override
    public int hashCode() {
        int result = rowNumber;
        result = 31 * result + (row != null ? row.hashCode() : 0);
        return result;
    }
}
