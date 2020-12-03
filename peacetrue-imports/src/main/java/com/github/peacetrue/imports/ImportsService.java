package com.github.peacetrue.imports;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiayx
 */
public interface ImportsService {

    /** 导入数据 */
    <T> ImportsResult<T> imports(InputStream inputStream, ImportsSetting setting) throws IOException;

}
