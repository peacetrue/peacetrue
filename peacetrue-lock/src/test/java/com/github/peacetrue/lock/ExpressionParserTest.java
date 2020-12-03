package com.github.peacetrue.lock;

import com.github.peacetrue.lock.client.DTO;
import com.github.peacetrue.lock.client.LockClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author xiayx
 */
public class ExpressionParserTest {

    @Test
    public void name() throws Exception {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("hello #{root[0].userName}", ParserContext.TEMPLATE_EXPRESSION);
//        EvaluationContext context = new MethodBasedEvaluationContext(new LockClientServiceImpl(),LockClientServiceImpl.class.getMethod("doSomething", DTO.class),new Object[]{new DTO()}, new AspectJAdviceParameterNameDiscoverer())
//        expression.getValue()
    }
}
