package me.Animemchik.modules.std;

import me.Animemchik.lib.Function;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;

public final class std_time implements Function {

    @Override
    public Value execute(Value... args) {
        return NumberValue.of(System.currentTimeMillis());
    }
}