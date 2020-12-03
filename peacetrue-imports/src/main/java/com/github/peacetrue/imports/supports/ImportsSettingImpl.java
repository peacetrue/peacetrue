package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ImportsSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportsSettingImpl implements ImportsSetting {

    private Charset charset = StandardCharsets.UTF_8;
    private String[] header;
    private int maxRowCount = Integer.MAX_VALUE;
}
