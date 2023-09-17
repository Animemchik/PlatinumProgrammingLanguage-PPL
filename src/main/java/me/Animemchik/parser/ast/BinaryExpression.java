package me.Animemchik.parser.ast;

import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Value;

public final class BinaryExpression implements Expression {
    public final Expression expr1, expr2;
    private final char operation;

    public BinaryExpression(char operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() {
        final Value value1 = expr1.eval();
        final Value value2 = expr2.eval();
        if (value1 instanceof StringValue) {
            final String string1 = value1.asString();
            switch (operation) {
                case '*' -> {
                    final int iterations = value2.asInteger();
                    return new StringValue(String.valueOf(string1).repeat(Math.max(0, iterations)));
                }
                case '/' -> throw new RuntimeException("Can't use division operator '/' with string");
                case '-' -> throw new RuntimeException("Can't use minus operator '-' with string");
                case '+' -> {
                    return new StringValue(string1 + value2.asString());
                }
            }
        }
        final double num1 = value1.asNumber();
        final double num2 = value2.asNumber();
        switch (operation) {
            case '-': return new NumberValue(num1 - num2);
            case '*': return new NumberValue(num1 * num2);
            case '/': return new NumberValue(num1 / num2);
            case '%': return new NumberValue(num1 % num2);
            case '+':
            default:
                return new NumberValue(num1 + num2);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expr1, operation, expr2);
    }
}
