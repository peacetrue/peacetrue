package com.github.peacetrue.imports;

import java.nio.charset.Charset;

/**
 * @author xiayx
 */
public interface ImportsSetting {

    /** 获取字符集 */
    Charset getCharset();

    /** 获取表头 */
    String[] getHeader();

    /** 获取允许读取的最大行数(含表头、空行) */
    int getMaxRowCount();
}