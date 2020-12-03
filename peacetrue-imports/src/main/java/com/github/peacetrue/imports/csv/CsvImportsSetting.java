package com.github.peacetrue.imports.csv;

import com.github.peacetrue.imports.supports.ImportsSettingImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;

/**
 * @author xiayx
 */
@Getter
@Setter
@ToString(callSuper = true)
public class CsvImportsSetting extends ImportsSettingImpl {

    private String separator = ",";

    public CsvImportsSetting() {
    }

    public CsvImportsSetting(Charset charset, String[] header, int maxRowCount) {
        super(charset, header, maxRowCount);
    }
}
