package com.github.peacetrue.imports;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author xiayx
 */
public interface ImportsInputStreamProcessor {

    /** 读取导入数据 */
    void processInputStream(InputStream inputStream, ImportsContext importsContext) throws IOException;

}
