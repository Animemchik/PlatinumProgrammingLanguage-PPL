package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class std_charat implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);

        final String input = args[0].asString();
        final int index = args[1].asInteger();
        
        return NumberValue.of((short)input.charAt(index));
    }
}
