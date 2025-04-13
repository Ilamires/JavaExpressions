package com.ExpressionTest;

import com.Expressions.Expression;
import org.junit.Test;
import org.junit.Assert;

import java.util.LinkedList;

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
        expression = new Expression("-(3/2)* y6");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(3/2) * y y6y");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(-3/(-2))*y");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-(-3/(-2.1))*y");
        result = result && expression.IsCorrectExpression();
        expression = new Expression("-( -3 /((-2 .1)  +3)) * y");
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
        expression = new Expression("-(-3/(-2.1))*y.1");
        result = result && !expression.IsCorrectExpression();
        expression = new Expression("-( -3 /((-.1)+3)) * y");
        result = result && !expression.IsCorrectExpression();

        Assert.assertTrue(result);
    }

    @Test
    public void ExpressionCalculateExpressionTest() {
        float E = 0.00001f;
        boolean result;
        Expression expression = new Expression("-(-(-2.1+ (-5) + 4)*x+3/(3-4)*4/7*8)*xy");
        LinkedList<Float> variables = new LinkedList<Float>();
        variables.add(5.0f);
        variables.add(3.0f);
        result = Math.abs(expression.CalculateExpression(variables) -
                (-(-(-2.1 + (-5.0) + 4.0) * 5.0 + 3.0 / (3.0 - 4.0) * 4.0 / 7.0 * 8.0) * 3.0)) < E;

        expression = new Expression("-(-(-2.1+ (-5.6) - d*xy) *x+3/(3.2-4)+6*x)/(-xy)");
        variables.clear();
        variables.add(8.0f);
        variables.add(-4.0f);
        variables.add(-11.0f);

        result = result && Math.abs(expression.CalculateExpression(variables) -
                (-(-(-2.1 + (-5.6) - 8.0 * -4.0) * (-11.0) + 3 / (3.2 - 4) + 6 * (-11.0)) / (-(-4.0)))) < E;

        Assert.assertTrue(result);
    }
}
