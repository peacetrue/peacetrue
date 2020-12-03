package com.github.peacetrue.imports;

import java.util.List;

/**
 * @author xiayx
 */
public interface ImportsSaver<T> {

    /** 保存导入数据 */
    void save(List<RowNumberWrapper<T>> records, ImportsContext importsContext);

}
