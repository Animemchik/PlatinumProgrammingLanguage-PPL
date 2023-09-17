package me.Animemchik.modules.std;

import me.Animemchik.exceptions.ArgumentsMismatchException;
import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class std_join implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkRange(1, 4, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new TypeException("Array expected in first argument");
        }
        
        final ArrayValue array = (ArrayValue) args[0];
        switch (args.length) {
            case 1:
                return ArrayValue.joinToString(array, "", "", "");
            case 2:
                return ArrayValue.joinToString(array, args[1].asString(), "", "");
            case 3:
                return ArrayValue.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString());
            case 4:
                return ArrayValue.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString());
            default:
                throw new ArgumentsMismatchException("Wrong number of arguments");
        }
    }
}
