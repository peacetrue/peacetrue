package com.github.peacetrue.pagehelper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.github.peacetrue.mybatis.dynamic.MybatisDynamicUtils.propertyNameToColumnName;

/**
 * 分页工具类
 *
 * @author xiayx
 * @see PageHelper
 */
public abstract class PageHelperUtils {

    /** {@link PageHelper#startPage(int, int, String)}适配{@link Pageable} */
    public static <T> Page<T> startPage(Pageable pageable) {
        Page<T> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        if (pageable.getSort() != null) {page.setOrderBy(toOrderBy(pageable.getSort()));}
        return page;
    }

    /** {@link Sort}转换成OrderBy字符串 */
    public static String toOrderBy(Sort sort) {
        return StreamSupport.stream(sort.spliterator(), false)
                .map(order -> propertyNameToColumnName(order.getProperty()) + " " + order.getDirection().name())
                .collect(Collectors.joining(","));
    }

    /** 获取总数据条数 */
    public static long getTotal(List list) {
        if (!(list instanceof Page)) {throw new IllegalArgumentException("the list must be a Page");}
        return ((Page) list).getTotal();
    }
}
