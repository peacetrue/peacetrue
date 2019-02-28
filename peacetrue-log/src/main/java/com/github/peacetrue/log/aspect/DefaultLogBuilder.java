package com.github.peacetrue.log.aspect;

import com.github.peacetrue.log.LogProperties;
import com.github.peacetrue.log.service.AbstractLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;

import javax.annotation.PostConstruct;

/**
 * 默认的日志构建器
 *
 * @author xiayx
 */
public class DefaultLogBuilder extends AbstractLogBuilder {

    @Autowired
    private ExpressionParser expressionParser;
    @Autowired
    private LogProperties logProperties;
    private Class<?> recordIdType;
    @Autowired
    private CreatorIdProvider creatorIdProvider;

    @PostConstruct
    private void init() {
        recordIdType = BeanUtils.getPropertyDescriptor(logProperties.getConcreteClass(), "recordId").getPropertyType();
    }

    @Override
    protected AbstractLog instance(Context context) {
        return BeanUtils.instantiate(logProperties.getConcreteClass());
    }

    @Override
    protected String parseModuleCode(Context context) {
        return context.getBean().getClass().getAnnotation(Module.class).code();
    }

    @Override
    protected Object parseRecordId(Context context) {
        LogInfo logInfo = context.getMethod().getAnnotation(LogInfo.class);
        if (logInfo.recordId().equals("")) return null;
        Expression expression = expressionParser.parseExpression(logInfo.recordId());
        return expression.getValue(context, recordIdType);
    }

    @Override
    protected String parseOperateCode(Context context) {
        return context.getMethod().getAnnotation(Operate.class).code();
    }

    @Override
    protected String parseDescription(Context context) {
        LogInfo logInfo = context.getMethod().getAnnotation(LogInfo.class);
        Expression expression = expressionParser.parseExpression(logInfo.description(), ParserContext.TEMPLATE_EXPRESSION);
        return expression.getValue(context, String.class);
    }

    @Override
    protected Object parseCreatorId(Context context) {
        return creatorIdProvider.get(context);
    }
}
