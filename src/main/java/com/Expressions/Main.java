package com.Expressions;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        Expression expression = new Expression("-(-(3+2))/5");
        LinkedList<Float> variables = new LinkedList<Float>();
        variables.add(3.0f);
        variables.add(5.0f);
        System.out.println(expression.CalculateExpression(variables));
    }

}