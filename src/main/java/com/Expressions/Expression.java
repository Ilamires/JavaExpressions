package com.Expressions;

import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;

public class Expression {
    String expressionString;


    public Expression(String expressionString) {
        SetExpression(expressionString);
    }
    public void SetExpression(String expressionString) {
        this.expressionString = expressionString.replaceAll(" ", "");
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
                    result = " +-*/(".contains(String.valueOf(lastSymbol));
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

    private StringBuilder SubstituteVariables(LinkedList<Float> variable_values) {
        StringBuilder variable = new StringBuilder();
        StringBuilder realExpressionStringBuilder = new StringBuilder();
        boolean isVariable = false;
        HashMap<String, Float> variables = new HashMap<String, Float>();
        int i = 0;
        for (char symbol : expressionString.toCharArray()) {
            if (Character.isDigit(symbol) || Character.isAlphabetic(symbol)) {
                isVariable = isVariable || Character.isAlphabetic(symbol);
                if (isVariable) {
                    variable.append(symbol);
                }
            } else if (isVariable) {
                isVariable = false;
                if (variable_values.size() <= i)
                    throw new IndexOutOfBoundsException("Not enough variable values");
                if (!variables.containsKey(variable.toString())) {
                    variables.put(variable.toString(), variable_values.get(i));
                    ++i;
                }
                if (variables.get(variable.toString()) < 0)
                    realExpressionStringBuilder.append("(")
                            .append(variables.get(variable.toString()).toString()).append(")");
                else
                    realExpressionStringBuilder.append(variables.get(variable.toString()).toString());
                variable = new StringBuilder();
            }
            if (!isVariable)
                realExpressionStringBuilder.append(symbol);
        }
        if (isVariable) {
            if (variable_values.size() <= i)
                throw new IndexOutOfBoundsException("Not enough variable values");
            variables.put(variable.toString(), variable_values.get(i));
            if (variables.get(variable.toString()) < 0)
                realExpressionStringBuilder.append("(")
                        .append(variables.get(variable.toString()).toString()).append(")");
            else
                realExpressionStringBuilder.append(variables.get(variable.toString()).toString());
        }
        return realExpressionStringBuilder;
    }

    public float CalculateExpression(LinkedList<Float> variable_values) {
        if (!IsCorrectExpression())
            throw new ArithmeticException("Expression isn't correct");
        String realExpressionString = SubstituteVariables(variable_values).toString();
        String number;
        Stack<Character> Operations = new Stack<>();
        Stack<String> Others = new Stack<>();
        int i = 0;
        while (i < realExpressionString.length()) {
            if ("+-*/".contains(String.valueOf(realExpressionString.charAt(i)))) {
                if (realExpressionString.charAt(i) == '-' &&
                        (i == 0 || realExpressionString.charAt(i - 1) == '(')
                        && realExpressionString.charAt(i + 1) == '(') {
                    Others.push(String.valueOf(realExpressionString.charAt(i)));
                    ++i;
                } else if (realExpressionString.charAt(i) == '-' &&
                        (i == 0 || realExpressionString.charAt(i - 1) == '(')) {
                    StringBuilder numberBuilder = new StringBuilder();
                    do {
                        numberBuilder.append(realExpressionString.charAt(i));
                        ++i;
                    } while (i < realExpressionString.length() &&
                            (Character.isDigit(realExpressionString.charAt(i)) ||
                                    realExpressionString.charAt(i) == '.'));
                    Others.push(numberBuilder.toString());
                } else {
                    Operations.push(realExpressionString.charAt(i));
                    ++i;
                }
            } else if (realExpressionString.charAt(i) == '(') {
                Others.push(String.valueOf(realExpressionString.charAt(i)));
                ++i;
            } else if (Character.isDigit(realExpressionString.charAt(i))) {
                StringBuilder numberBuilder = new StringBuilder();
                while (i < realExpressionString.length() &&
                        (Character.isDigit(realExpressionString.charAt(i)) || realExpressionString.charAt(i) == '.')) {
                    numberBuilder.append(realExpressionString.charAt(i));
                    ++i;
                }
                number = numberBuilder.toString();
                if (!Others.isEmpty() && !Others.peek().equals("(")) {
                    if (!Operations.isEmpty())
                        if (Operations.peek() == '*') {
                            Operations.pop();
                            number = Float.toString(Float.parseFloat(number) * Float.parseFloat(Others.pop()));
                        } else if (Operations.peek() == '/') {
                            Operations.pop();
                            number = Float.toString(Float.parseFloat(Others.pop()) / Float.parseFloat(number));
                        }
                }
                Others.push(number);
            } else if (realExpressionString.charAt(i) == ')') {
                number = Others.pop();
                while (!Others.isEmpty() && !Others.peek().equals("(")) {
                    if (Operations.peek() == '+') {
                        Operations.pop();
                        number = Float.toString(Float.parseFloat(number) + Float.parseFloat(Others.pop()));
                    } else if (Operations.peek() == '-') {
                        Operations.pop();
                        number = Float.toString(Float.parseFloat(Others.pop()) - Float.parseFloat(number));
                    }
                }
                if (!Others.isEmpty())
                    Others.pop();
                if (!Others.isEmpty() && Others.peek().equals("-")) {
                    Others.pop();
                    number = Float.toString(-Float.parseFloat(number));
                }

                if (!Others.isEmpty() && !Others.peek().equals("(")) {
                    if (!Operations.isEmpty())
                        if (Operations.peek() == '*') {
                            Operations.pop();
                            number = Float.toString(Float.parseFloat(number) * Float.parseFloat(Others.pop()));
                        } else if (Operations.peek() == '/') {
                            Operations.pop();
                            number = Float.toString(Float.parseFloat(Others.pop()) / Float.parseFloat(number));
                        }
                }
                Others.push(number);
                ++i;
            }
        }
        number = Others.pop();
        while (!Operations.isEmpty()) {
            if (Operations.peek() == '+') {
                Operations.pop();
                number = Float.toString(Float.parseFloat(number) + Float.parseFloat(Others.pop()));
            } else if (Operations.peek() == '-') {
                Operations.pop();
                number = Float.toString(Float.parseFloat(Others.pop()) - Float.parseFloat(number));
            }
        }
        return Float.parseFloat(number);
    }
}
