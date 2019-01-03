package com.github.peacetrue.export;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * 导出工具类
 *
 * @author xiayx
 */
public abstract class ExportUtils {

    public static void export(HttpServletResponse response,
                              String fileName,
                              Consumer<HttpServletResponse> consumer) throws IOException {
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        consumer.accept(response);
    }


}
