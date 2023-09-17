package me.Animemchik.modules.std;

import me.Animemchik.exceptions.TypeException;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class std_arraySplice implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkRange(2, 4, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new TypeException("Array expected at first argument");
        }
        final Value[] input = ((ArrayValue) args[0]).getCopyElements();
        final int size = input.length;

        int start = args[1].asInteger();
        if (start < 0) start = size - Math.abs(start);
        start = Math.max(0, Math.min(size, start));

        final int deleteCount = (args.length >= 3)
                ? Math.max(0, Math.min(size - start, args[2].asInteger())) // [0..size - start)
                : (size - start);

        final Value[] additions;
        if (args.length != 4) {
            additions = new Value[0];
        } else if (args[3].type() == Types.ARRAY) {
            additions = ((ArrayValue) args[3]).getCopyElements();
        } else {
            throw new TypeException("Array expected at fourth argument");
        }

        Value[] result = new Value[start + (size - start - deleteCount) + additions.length];
        System.arraycopy(input, 0, result, 0, start); // [0, start)
        System.arraycopy(additions, 0, result, start, additions.length); // insert new
        System.arraycopy(input, start + deleteCount, result, start + additions.length, size - start - deleteCount);
        return new ArrayValue(result);
    }
    
}
