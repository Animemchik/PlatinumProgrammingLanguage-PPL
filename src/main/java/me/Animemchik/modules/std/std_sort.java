package me.Animemchik.modules.std;

import me.Animemchik.exceptions.ArgumentsMismatchException;
import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;
import me.Animemchik.lib.ValueUtils;
import java.util.Arrays;

public final class std_sort implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkAtLeast(1, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new TypeException("Array expected in first argument");
        }
        final Value[] elements = ((ArrayValue) args[0]).getCopyElements();
        
        switch (args.length) {
            case 1:
                Arrays.sort(elements);
                break;
            case 2:
                final Function comparator = ValueUtils.consumeFunction(args[1], 1);
                Arrays.sort(elements, (o1, o2) -> comparator.execute(o1, o2).asInteger());
                break;
            default:
                throw new ArgumentsMismatchException("Wrong number of arguments");
        }
        
        return new ArrayValue(elements);
    }
    
}
