package me.Animemchik.lib;

import me.Animemchik.parser.ast.ReturnStatement;
import me.Animemchik.parser.ast.Statement;

import java.util.List;

public final class UserDefineFunction implements Function {

    private final List<String> argNames;
    private final Statement body;

    public UserDefineFunction(List<String> argNames, Statement body) {
        this.argNames = argNames;
        this.body = body;
    }

    public int getArgsCount() {
        return argNames.size();
    }

    public String getArgName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return argNames.get(index);
    }

    @Override
    public Value execute(Value... args) {
        try {
            body.execute();
        } catch (ReturnStatement rs) {
            return rs.getResult();
        }
        return NumberValue.ZERO;
    }
}
