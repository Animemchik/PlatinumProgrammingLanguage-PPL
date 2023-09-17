package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class std_sprintf implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkAtLeast(1, args.length);
        
        final String format = args[0].asString();
        final Object[] values = new Object[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            values[i - 1] = (args[i].type() == Types.NUMBER)
                    ? args[i].raw()
                    : args[i].asString();
        }
        return new StringValue(String.format(format, values));
    }
}
