package com.Expressions;

import java.util.Stack;

public class Expression {
    String expressionString;


    public Expression(String expressionString) {
        this.expressionString = expressionString;
    }

    public boolean IsCorrectExpression() {
        boolean result = true;
        Stack<Character> correctStaples = new Stack<>();
        char lastSymbol = ' ';
        for (char symbol : expressionString.toCharArray()) {
            if (result) {
                if ("({[".contains(String.valueOf(symbol))) {
                    result = " +-*/".contains(String.valueOf(lastSymbol));
                    if (result)
                        correctStaples.push(symbol);
                } else if (")]}".contains(String.valueOf(symbol))) {
                    result = !correctStaples.isEmpty();
                    if (result) {
                        char lastStaple = correctStaples.pop();
                        result = !"+-*/".contains(String.valueOf(lastSymbol)) && switch (symbol) {
                            case ')' -> lastStaple == '(' && lastSymbol != '(';
                            case ']' -> lastStaple == '[' && lastSymbol != '[';
                            case '}' -> lastStaple == '{' && lastSymbol != '{';
                            default -> true;
                        };
                    }
                } else if (Character.isDigit(symbol) || Character.isAlphabetic(symbol)) {
                    result = !")]}".contains(String.valueOf(lastSymbol)) &&
                            (!(Character.isDigit(lastSymbol) && Character.isAlphabetic(symbol)));
                } else if ("+-*/".contains(String.valueOf(symbol))) {
                    if (symbol != '-')
                        result = !" ([{+-*/".contains(String.valueOf(lastSymbol));
                    else
                        result = !"+-*/".contains(String.valueOf(lastSymbol));
                } else {
                    result = false;
                }
                lastSymbol = symbol;
            }
        }
        result = result && correctStaples.isEmpty();
        return result;
    }
}
