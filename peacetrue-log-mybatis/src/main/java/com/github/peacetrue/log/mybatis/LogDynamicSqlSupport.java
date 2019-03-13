package com.github.peacetrue.log.mybatis;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class LogDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final Log log = new Log();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> id = log.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> moduleCode = log.moduleCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> recordId = log.recordId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> operateCode = log.operateCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> description = log.description;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> creatorId = log.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createdTime = log.createdTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class Log extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> moduleCode = column("module_code", JDBCType.VARCHAR);

        public final SqlColumn<String> recordId = column("record_id", JDBCType.VARCHAR);

        public final SqlColumn<String> operateCode = column("operate_code", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("description", JDBCType.VARCHAR);

        public final SqlColumn<String> creatorId = column("creator_id", JDBCType.VARCHAR);

        public final SqlColumn<Date> createdTime = column("created_time", JDBCType.TIMESTAMP);

        public Log() {
            super("log");
        }
    }
}