package com.github.peacetrue.imports;

/**
 * @author xiayx
 */
public interface ImportsRowProcessor<T> {

    /** 读取导入数据 */
    void processRow(RowNumberWrapper<T[]> rows, ImportsContext importsContext);

}
