package me.Animemchik.parser.ast;

import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class UnaryExpression implements Expression {
    public final Expression expr;
    public final char operation;

    public UnaryExpression(char operation, Expression expr) {
        this.operation = operation;
        this.expr = expr;
    }

    @Override
    public Value eval() {
        if (operation == '-') return new NumberValue(-expr.eval().asNumber());
        return new NumberValue(expr.eval().asNumber());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%c%s)", operation, expr);
    }
}
