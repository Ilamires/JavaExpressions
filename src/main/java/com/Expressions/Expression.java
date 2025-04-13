package com.Expressions;

import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;

public class Expression {
    String expressionString;


    public Expression(String expressionString) {
        this.expressionString = expressionString;
    }

    public boolean IsCorrectExpression() {
        boolean result = true;
        Stack<Character> correctStaples = new Stack<>();
        char lastSymbol = ' ';
        boolean isVariable = false;
        result = !"+-*/".contains(String.valueOf(expressionString.charAt(expressionString.length() - 1)));
        for (char symbol : expressionString.toCharArray()) {
            if (result) {
                if (symbol == '.') {
                    if (!Character.isDigit(lastSymbol) || isVariable) {
                        result = false;
                    }
                } else if ("({[".contains(String.valueOf(symbol))) {
                    isVariable = false;
                    result = " +-*/".contains(String.valueOf(lastSymbol));
                    if (result)
                        correctStaples.push(symbol);
                } else if (")]}".contains(String.valueOf(symbol))) {
                    isVariable = false;
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
                            (!(Character.isDigit(lastSymbol) && Character.isAlphabetic(symbol)) || isVariable);
                    isVariable = isVariable || Character.isAlphabetic(symbol);
                } else if ("+-*/".contains(String.valueOf(symbol))) {
                    isVariable = false;
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

    public double CalculateExpression(LinkedList<Double> variable_values) {
        double result = 1.0;
        StringBuilder variable = new StringBuilder();
        boolean isVariable = false;
        HashMap<String, Double> variables = new HashMap<String, Double>();
        int i = 0;
        for (char symbol : expressionString.toCharArray()) {
            if (Character.isDigit(symbol) || Character.isAlphabetic(symbol)) {
                isVariable = isVariable || Character.isAlphabetic(symbol);
                variable.append(symbol);
            } else if (isVariable) {
                isVariable = false;
                variables.put(variable.toString(), variable_values.get(i));
                variable = new StringBuilder();
                ++i;
            }
        }
        i = 0;
        while (i < expressionString.length()) {
            while (i < expressionString.length() && "({[".contains(String.valueOf(expressionString.charAt(i)))) {
                ++i;
            }
            if (i < expressionString.length() && Character.isAlphabetic(expressionString.charAt(i))) {
                variable = new StringBuilder();
                while (i < expressionString.length() && Character.isDigit(expressionString.charAt(i)) ||
                        Character.isAlphabetic(expressionString.charAt(i))) {
                    variable.append(expressionString.charAt(i));
                    ++i;
                }
            }
            if (i < expressionString.length() && Character.isDigit(expressionString.charAt(i))) {
                StringBuilder number = new StringBuilder();
                while (i < expressionString.length() && Character.isDigit(expressionString.charAt(i))) {
                    number.append(expressionString.charAt(i));
                    ++i;
                }
            }
            while (i < expressionString.length() && "+-".contains(String.valueOf(expressionString.charAt(i)))) {
                ++i;
            }
            while (i < expressionString.length() && "*/".contains(String.valueOf(expressionString.charAt(i)))) {
                ++i;
            }
            while (i < expressionString.length() && ")]}".contains(String.valueOf(expressionString.charAt(i)))) {
                ++i;
            }
        }

        return result;
    }
}
