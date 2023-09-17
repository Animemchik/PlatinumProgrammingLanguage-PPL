package me.Animemchik.parser.ast;

import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Value;
import me.Animemchik.lib.Variables;
import me.Animemchik.parser.TokenType;

public final class AssignmentEqStatement implements Statement{

    public final String variable;
    public final TokenType operation;
    public final Expression expression;

    public AssignmentEqStatement(String variable, TokenType operation, Expression expression) {
        this.variable = variable;
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value result = null;
        final Value var = Variables.get(variable);
        final Value expr = expression.eval();
        if (var instanceof StringValue) {
            switch (operation) {
                case PLUSEQ -> result = new StringValue(var.asString() + expr.asString());
                case MULTIEQ -> {
                    final int iterations = expr.asInteger();
                    result = new StringValue(String.valueOf(var.asString()).repeat(Math.max(0, iterations)));
                }
                default -> throw new RuntimeException("Unexpected exception");
            }
        } else if (var instanceof NumberValue) {
            switch (operation) {
                case PLUSEQ -> result = new NumberValue(var.asNumber() + expr.asNumber());
                case MINUSEQ -> result = new NumberValue(var.asNumber() - expr.asNumber());
                case MULTIEQ -> result = new NumberValue(var.asNumber() * expr.asNumber());
                case DIVIDEEQ -> result = new NumberValue(var.asNumber() / expr.asNumber());
                case PERCENTEQ -> result = new NumberValue(var.asNumber() % expr.asNumber());
                default -> throw new RuntimeException("Unexpected exception");
            }
        }
        Variables.set(variable, result);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        final String op;
        switch (operation) {
            case PLUSEQ -> op = "+=";
            case MINUSEQ -> op = "-=";
            case MULTIEQ -> op = "*=";
            case DIVIDEEQ -> op = "/=";
            case PERCENTEQ -> op = "%=";
            default -> throw new RuntimeException("Unexpected exception");
        }
        return String.format("%s %s %s", variable, op, expression);
    }
}
