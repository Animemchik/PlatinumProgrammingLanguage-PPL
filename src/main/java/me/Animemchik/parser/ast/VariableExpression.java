package me.Animemchik.parser.ast;

import me.Animemchik.lib.Value;
import me.Animemchik.lib.Variables;

public final class VariableExpression implements Expression{
    public final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Constant doesn't exists");
        return Variables.get(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
