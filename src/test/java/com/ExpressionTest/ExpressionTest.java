package com.ExpressionTest;

import com.Expressions.Expression;
import org.junit.Test;
import org.junit.Assert;

public class ExpressionTest {

    @Test
    public void ExpressionIsCorrectExpressionTest() {
        boolean result;
        //Correct expressions
        Expression expression = new Expression("(3+2)*6");
        result = expression.IsCorrectExpression();
        expression = new Expression("-(3+2)*6");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(3/2)*6");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(x3/2)*6");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(3/2)*y6");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(3/2)*yy6y");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(-3/(-2))*y");
        result = result && expression.IsCorrectExpression();

        //Incorrect expressions
        expression = new Expression("*(3+2)*6");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-[(3+2])*6");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-({3+2)*6");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-(3/2)*6y");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-(3/2-)*y");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-(3/2+)*y");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-6(3/2)*y");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-(3/2)6*y");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-(3/2)*y+");
        result = result && !expression.IsCorrectExpression();

        Assert.assertTrue(result);
    }
}
