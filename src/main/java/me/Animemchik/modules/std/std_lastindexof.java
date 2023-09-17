package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class std_lastindexof implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final String input = args[0].asString();
        final String what = args[1].asString();
        if (args.length == 2) {
            return NumberValue.of(input.lastIndexOf(what));
        }
        final int index = args[2].asInteger();
        return NumberValue.of(input.lastIndexOf(what, index));
    }
}
