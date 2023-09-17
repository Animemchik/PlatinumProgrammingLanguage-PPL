package me.Animemchik.modules.std;

import me.Animemchik.lib.*;

public final class std_length implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);

        final Value val = args[0];
        final int length;
        switch (val.type()) {
            case ARRAY:
                length = ((ArrayValue) val).size();
                break;
            case MAP:
                length = ((MapValue) val).size();
                break;
            case STRING:
                length = ((StringValue) val).length();
                break;
            case FUNCTION:
                final Function func = ((FunctionValue) val).getValue();
                if (func instanceof UserDefinedFunction) {
                    length = ((UserDefinedFunction) func).getArgsCount();
                } else {
                    length = 0;
                }
                break;
            default:
                length = 0;
                
        }
        return NumberValue.of(length);
    }
}