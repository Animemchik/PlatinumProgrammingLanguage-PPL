package me.Animemchik.modules.std;

import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.MapValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class std_arrayCombine implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new TypeException("Array expected in first argument");
        }
        if (args[1].type() != Types.ARRAY) {
            throw new TypeException("Array expected in second argument");
        }
        
        final ArrayValue keys = ((ArrayValue) args[0]);
        final ArrayValue values = ((ArrayValue) args[1]);
        final int length = Math.min(keys.size(), values.size());
        
        final MapValue result = new MapValue(length);
        for (int i = 0; i < length; i++) {
            result.set(keys.get(i), values.get(i));
        }
        return result;
    }
    
}
