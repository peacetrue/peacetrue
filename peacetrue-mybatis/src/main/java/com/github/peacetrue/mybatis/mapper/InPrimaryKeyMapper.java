package com.github.peacetrue.mybatis.mapper;

import java.util.List;

/**
 * 根据主键集合查询
 *
 * @author xiayx
 */
public interface InPrimaryKeyMapper<Id, Record> {

    /** 根据主键集合查询 */
    List<Record> selectInPrimaryKey(List<Id> ids);

}
