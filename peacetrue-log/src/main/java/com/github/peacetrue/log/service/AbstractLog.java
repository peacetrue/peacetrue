package com.github.peacetrue.log.service;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽象日志类。主键类型不确定，一般而言，某个系统的主键类型应该是一致的
 *
 * @param <Id>        the type of {@link #id}
 * @param <RecordId>  the type of {@link #recordId}
 * @param <CreatorId> the type of {@link #creatorId}
 * @author xiayx
 */
@Data
public abstract class AbstractLog<Id, RecordId, CreatorId> implements Serializable {

    private Id id;
    private String moduleCode;
    private RecordId recordId;
    private String operateCode;
    private String description;
    private CreatorId creatorId;
    private Date createdTime;
}
