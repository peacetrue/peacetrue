package com.github.peacetrue.associate;

import com.github.peacetrue.spring.util.BeanUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.function.Function;

/**
 * @author xiayx
 */
public abstract class AssociatedSourceBuilderUtils {

    public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static <D, I> Function<D, I> getPropertyValue() {
        return getPropertyValue("id");
    }

    @SuppressWarnings("unchecked")
    public static <D, I> Function<D, I> getPropertyValue(String propertyName) {
        return d -> (I) BeanUtils.getPropertyValue(d, propertyName);
    }

    public static <D, R> Function<D, R> getExpressionValue(String expression, Class<R> desiredResultType) {
        return d -> EXPRESSION_PARSER.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(d, desiredResultType);
    }


}
