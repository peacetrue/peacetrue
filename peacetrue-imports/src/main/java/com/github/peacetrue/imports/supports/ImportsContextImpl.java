package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ImportsContext;
import com.github.peacetrue.imports.ImportsResult;
import com.github.peacetrue.imports.ImportsSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportsContextImpl implements ImportsContext {
    private ImportsSetting importsSetting;
    private ImportsResult importsResult;
}
