package com.github.peacetrue.imports;

import java.util.List;

/**
 * @author xiayx
 */
public interface ImportsChecker<T> {

    /** 检查导入数据 */
    void check(List<RowNumberWrapper<T>> records, ImportsContext importsContext);

}
