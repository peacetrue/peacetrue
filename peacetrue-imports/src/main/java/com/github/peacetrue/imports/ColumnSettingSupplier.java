package com.github.peacetrue.imports;

/**
 * @author xiayx
 */
public interface ColumnSettingSupplier<Column> {

    ColumnSetting getColumnSetting(Column column);

}
