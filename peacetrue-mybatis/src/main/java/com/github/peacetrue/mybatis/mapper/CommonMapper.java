package com.github.peacetrue.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;
import java.util.Map;

/**
 * 通用的Mapper，用于处理泛化的返回结果
 *
 * @author xiayx
 */
@Mapper
public interface CommonMapper {

    /** 新增 */
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "record.id")
    int insert(InsertStatementProvider<?> insertStatement);

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

    /** 获取数量 */
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    /** 获取数量 */
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample(SqlTable sqlTable) {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count()).from(sqlTable);
    }

    /** 更新 */
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    /** 更新 */
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(SqlTable sqlTable) {
        return UpdateDSL.updateWithMapper(this::update, sqlTable);
    }

    /** 删除 */
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    /** 删除 */
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample(SqlTable sqlTable) {
        return DeleteDSL.deleteFromWithMapper(this::delete, sqlTable);
    }

}
