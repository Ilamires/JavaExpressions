package com.Expressions;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        Expression expression = new Expression("-((-2.1+5)+3/(3-4)*7)*y");
        LinkedList<Float> variables = new LinkedList<Float>();
        variables.add(3.0f);
        System.out.println(expression.CalculateExpression(variables));
    }

}