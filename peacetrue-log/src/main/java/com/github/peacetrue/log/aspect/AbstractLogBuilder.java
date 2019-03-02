package com.github.peacetrue.log.aspect;

import com.github.peacetrue.log.service.AbstractLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 抽象的日志构建器
 *
 * @author xiayx
 */
public abstract class AbstractLogBuilder implements LogBuilder {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @SuppressWarnings("unchecked")
    public AbstractLog build(AfterMethodBasedEvaluationContext context) {
        logger.info("构建日志: {}", context);
        AbstractLog log = instance(context);
        log.setId(parseId(context));
        log.setModuleCode(parseModuleCode(context));
        log.setRecordId(parseRecordId(context));
        log.setOperateCode(parseOperateCode(context));
        log.setDescription(parseDescription(context));
        log.setCreatorId(parseCreatorId(context));
        log.setCreatedTime(new Date());
        return log;
    }

    /** 实例化日志 */
    protected abstract AbstractLog instance(AfterMethodBasedEvaluationContext context);

    /** 解析主键 */
    protected Object parseId(AfterMethodBasedEvaluationContext context) {return null;}

    /** 解析模块编码 */
    protected abstract String parseModuleCode(AfterMethodBasedEvaluationContext context);

    /** 解析记录主键 */
    protected abstract Object parseRecordId(AfterMethodBasedEvaluationContext context);

    /** 解析操作编码 */
    protected abstract String parseOperateCode(AfterMethodBasedEvaluationContext context);

    /** 解析操作描述 */
    protected abstract String parseDescription(AfterMethodBasedEvaluationContext context);

    /** 解析创建者主键 */
    protected abstract Object parseCreatorId(AfterMethodBasedEvaluationContext context);

}
