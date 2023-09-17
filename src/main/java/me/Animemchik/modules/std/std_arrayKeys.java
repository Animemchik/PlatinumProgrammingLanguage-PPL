package me.Animemchik.modules.std;

import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.MapValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class std_arrayKeys implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);
        if (args[0].type() != Types.MAP) {
            throw new TypeException("Map expected in first argument");
        }
        final MapValue map = ((MapValue) args[0]);
        final List<Value> keys = new ArrayList<>(map.size());
        for (Map.Entry<Value, Value> entry : map) {
            keys.add(entry.getKey());
        }
        return new ArrayValue(keys);
    }
    
}
