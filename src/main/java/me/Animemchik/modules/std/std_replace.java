package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Value;

public final class std_replace implements Function {
    
    @Override
    public Value execute(Value... args) {
        Arguments.check(3, args.length);
        
        final String input = args[0].asString();
        final String target = args[1].asString();
        final String replacement = args[2].asString();
        
        return new StringValue(input.replace(target, replacement));
    }
}
