package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.PropertyColumnSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyColumnSettingImpl<T> extends ColumnSettingImpl<T> implements PropertyColumnSetting<T> {
    private String propertyName;
}
