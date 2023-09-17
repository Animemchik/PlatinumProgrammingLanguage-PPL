package me.Animemchik.modules.std;

import me.Animemchik.parser.Console;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class std_echo implements Function {

    @Override
    public Value execute(Value... args) {
        final StringBuilder sb = new StringBuilder();
        for (Value arg : args) {
            sb.append(arg.asString());
            sb.append(" ");
        }
        Console.println(sb.toString());
        return NumberValue.ZERO;
    }
}
