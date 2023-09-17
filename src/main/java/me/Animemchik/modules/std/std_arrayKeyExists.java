package me.Animemchik.modules.std;

import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.MapValue;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class std_arrayKeyExists implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        if (args[1].type() != Types.MAP) {
            throw new TypeException("Map expected in second argument");
        }
        final MapValue map = ((MapValue) args[1]);
        return NumberValue.fromBoolean(map.containsKey(args[0]));
    }
    
}
