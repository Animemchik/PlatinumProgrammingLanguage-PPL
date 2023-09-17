package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class std_sleep implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);
        
        try {
            Thread.sleep((long) args[0].asNumber());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return NumberValue.ZERO;
    }
}