package me.Animemchik.parser.ast;

import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Value;
import me.Animemchik.lib.Variables;
import me.Animemchik.parser.TokenType;

public final class BinaryAssignmentExpression implements Expression {

    public final String variable;
    public final TokenType operation;

    public BinaryAssignmentExpression(String variable, TokenType operation) {
        this.variable = variable;
        this.operation = operation;
    }

    @Override
    public Value eval() {
        final Value var = Variables.get(variable);
        if (var instanceof NumberValue) {
            switch (operation) {
                case PLUSPLUS -> {
                    return new NumberValue(var.asNumber() + 1);
                }
                case MINUSMINUS -> {
                    return new NumberValue(var.asNumber() - 1);
                }
                default -> throw new RuntimeException("Unexpected exception");
            }
        } else {
            throw new RuntimeException("Number variable expected");
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
