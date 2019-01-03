package com.github.peacetrue.export;

import java.util.function.Function;

/**
 * 表头
 *
 * @author xiayx
 */
public interface Header {

    /** 获取单元格数目 */
    int getCount();

    /** 获取单元格 */
    Cell getCell(int i);

    /** 表头单元格 */
    interface Cell<T> {
        /** 获取单元格内容 */
        String getContent();

        /** 获取单元格格式化器，将对象转换为字符串 */
        Function<T, String> getFormatter();
    }

}
