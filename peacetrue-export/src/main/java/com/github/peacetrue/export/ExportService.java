package com.github.peacetrue.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * 导出服务
 *
 * @author xiayx
 */
public interface ExportService {

    /**
     * 导出
     *
     * @param out     输出流
     * @param header  表头
     * @param records 记录
     */
    default void export(OutputStream out, Header header, Iterable<?> records) throws IOException {
        export(new PrintWriter(out), header, records);
    }

    /**
     * 导出
     *
     * @param out     输出流
     * @param header  表头
     * @param records 记录
     */
    void export(Writer out, Header header, Iterable<?> records) throws IOException;


}
