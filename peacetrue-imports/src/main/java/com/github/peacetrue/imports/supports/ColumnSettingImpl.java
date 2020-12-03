package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ColumnSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnSettingImpl<T> implements ColumnSetting<T> {

    public static final ColumnSetting<String> STRING = new ColumnSettingImpl<>(Function.identity());
    public static final ColumnSetting<BigDecimal> BIG_DECIMAL = new ColumnSettingImpl<>(BigDecimal::new);

    private Function<String, T> parser;
}
