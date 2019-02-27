package com.github.peacetrue.associate.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 关联的表达式集合关联源
 *
 * @author xiayx
 */
public abstract class AssociatedExpressionCollectionAssociatedSource<I, D, R> extends IdPropertyCollectionAssociatedSource<I, D, R> {

    /** 关联的表达式字符串 */
    protected String expressionString;
    private Expression expression;
    private Class<R> typeClass;
    @Autowired
    protected ExpressionParser expressionParser;

    @PostConstruct
    @SuppressWarnings("unchecked")
    protected void init() {
        this.expression = expressionParser.parseExpression(expressionString, ParserContext.TEMPLATE_EXPRESSION);
        this.typeClass = (Class) ResolvableType.forClass(AssociatedExpressionCollectionAssociatedSource.class, this.getClass()).resolveGeneric(2);
    }

    protected AssociatedExpressionCollectionAssociatedSource(String expressionString) {
        this.expressionString = Objects.requireNonNull(expressionString);
    }

    protected AssociatedExpressionCollectionAssociatedSource(String idPropertyName, String expressionString) {
        super(idPropertyName);
        this.expressionString = Objects.requireNonNull(expressionString);
    }

    @Override
    @SuppressWarnings("unchecked")
    public R format(D data) {
        return this.expression.getValue(data, typeClass);
    }

}
