package com.github.peacetrue.imports.supports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassImportSetting extends ImportsSettingImpl {
    private Class modelClass;
}
