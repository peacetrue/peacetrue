package com.github.peacetrue.mybatis.mapper;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;
import java.util.Map;

/**
 * 通用的Mapper，用于处理泛化的返回结果
 *
 * @author xiayx
 */
public interface CommonMapper {

    /** 获取单个值 */
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    <T> T selectSingleObject(SelectStatementProvider selectStatement);

    /** 获取map对象 */
    @ResultType(Map.class)
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    <T> Map<String, T> selectSingleMap(SelectStatementProvider selectStatement);

    /** 获取集合对象 */
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    <T> List<T> selectListObject(SelectStatementProvider selectStatement);

    /** 获取集合map */
    @ResultType(Map.class)
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<Map<String, Object>> selectListMap(SelectStatementProvider selectStatement);

}
