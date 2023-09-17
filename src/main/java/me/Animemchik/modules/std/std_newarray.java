package me.Animemchik.modules.std;

import me.Animemchik.lib.ArrayValue;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class std_newarray implements Function {

    @Override
    public Value execute(Value... args) {
        return createArray(args, 0);
    }

    private ArrayValue createArray(Value[] args, int index) {
        final int size = args[index].asInteger();
        final int last = args.length - 1;
        ArrayValue array = new ArrayValue(size);
        if (index == last) {
            for (int i = 0; i < size; i++) {
                array.set(i, NumberValue.ZERO);
            }
        } else if (index < last) {
            for (int i = 0; i < size; i++) {
                array.set(i, createArray(args, index + 1));
            }
        }
        return array;
    }
}