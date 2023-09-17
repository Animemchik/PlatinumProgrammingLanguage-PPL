package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class NumberFunctions {

    private NumberFunctions() { }

    static Value toHexString(Value... args) {
        Arguments.check(1, args.length);
        long value;
        if (args[0].type() == Types.NUMBER) {
            value = ((NumberValue) args[0]).asLong();
        } else {
            value = (long) args[0].asNumber();
        }
        return new StringValue(Long.toHexString(value));
    }
}
