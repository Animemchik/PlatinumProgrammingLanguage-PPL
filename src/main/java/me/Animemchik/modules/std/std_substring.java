package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Value;

public final class std_substring implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkOrOr(2, 3, args.length);
        
        final String input = args[0].asString();
        final int startIndex = args[1].asInteger();
        
        String result;
        if (args.length == 2) {
            result = input.substring(startIndex);
        } else {
            final int endIndex = args[2].asInteger();
            result = input.substring(startIndex, endIndex);
        }
        
        return new StringValue(result);
    }
}
