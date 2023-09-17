package me.Animemchik.parser.ast;

import me.Animemchik.lib.Functions;
import me.Animemchik.lib.UserDefineFunction;

import java.util.List;

public final class FunctionDefineStatement implements Statement {

    private final String name;
    private final List<String> argNames;
    public final Statement body;

    public FunctionDefineStatement(String name, List<String> argNames, Statement body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public void execute() {
        Functions.set(name, new UserDefineFunction(argNames, body));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "def (" + argNames.toString() + ") {\n" + body + '}';
    }
}
