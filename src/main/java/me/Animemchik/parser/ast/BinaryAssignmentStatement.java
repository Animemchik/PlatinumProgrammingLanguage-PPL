package me.Animemchik.parser.ast;

import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;
import me.Animemchik.lib.Variables;
import me.Animemchik.parser.TokenType;

public final class BinaryAssignmentStatement implements Statement{

    public final String variable;
    public final TokenType operation;
    public final BinaryAssignmentExpression expression;

    public BinaryAssignmentStatement(String variable, TokenType operation) {
        this.variable = variable;
        this.operation = operation;
        this.expression = new BinaryAssignmentExpression(variable, operation);
    }

    @Override
    public void execute() {
        Variables.set(variable, expression.eval());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        final String op;
        switch (operation) {
            case PLUSPLUS -> op = "++";
            case MINUSMINUS -> op = "--";
            default -> throw new RuntimeException("Unexpected exception");
        }
        return String.format("%s%s", variable, op);
    }
}
