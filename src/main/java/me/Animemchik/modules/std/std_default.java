package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.MapValue;
import me.Animemchik.lib.Value;

public final class std_default implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        if (isEmpty(args[0])) {
            return args[1];
        }
        return args[0];
    }

    private boolean isEmpty(Value value) {
        if (value == null || value.raw() == null) {
            return true;
        }
        switch (value.type()) {
            case NUMBER:
                return (value.asInteger() == 0);
            case STRING:
                return (value.asString().isEmpty());
            case ARRAY:
                return ((ArrayValue) value).size() == 0;
            case MAP:
                return ((MapValue) value).size() == 0;
            default:
                return false;
        }
    }
}