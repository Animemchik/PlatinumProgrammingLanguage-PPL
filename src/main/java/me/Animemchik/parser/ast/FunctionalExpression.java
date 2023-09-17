package me.Animemchik.parser.ast;

import me.Animemchik.lib.*;

import java.util.ArrayList;
import java.util.List;

public final class FunctionalExpression implements Expression {

    public final String name;
    public final List<Expression> arguments;

    public FunctionalExpression(String name) {
        this.name = name;
        this.arguments = new ArrayList<>();
    }

    public FunctionalExpression(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void addArgument(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public Value eval() {
        final int size = arguments.size();
        Value[] values = new Value[size];
        for (int i = 0; i < size; i++) {
            values[i] = arguments.get(i).eval();
        }
        final Function function = Functions.get(name);
        if (function instanceof UserDefineFunction userFunction) {
            if (size != userFunction.getArgsCount()) throw new RuntimeException("Args count mismatch.");

            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.set(userFunction.getArgName(i), values[i]);
            }
            final Value result = userFunction.execute(values);
            Variables.pop();
            return result;
        }
        return function.execute(values);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return name + '(' + arguments.toString() + ')';
    }
}
