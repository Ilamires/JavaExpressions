package com.Expressions;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        Expression expression = new Expression("-(-(-2.1+ (-5) + 4)*x+3/(3-4)*4/7*8)*xy");
        LinkedList<Float> variables = new LinkedList<Float>();
        variables.add(5.0f);
        variables.add(3.0f);
        System.out.println(expression.CalculateExpression(variables));
    }

}