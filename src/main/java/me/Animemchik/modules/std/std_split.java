package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.Value;

public final class std_split implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);

        final String input = args[0].asString();
        final String regex = args[1].asString();
        final int limit = (args.length == 3) ? args[2].asInteger() : 0;

        final String[] parts = input.split(regex, limit);
        return ArrayValue.of(parts);
    }
}
