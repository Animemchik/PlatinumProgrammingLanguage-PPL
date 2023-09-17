package me.Animemchik.modules.std;

import me.Animemchik.parser.Console;
import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.FunctionValue;
import me.Animemchik.lib.Functions;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Types;
import me.Animemchik.lib.Value;

public final class std_thread implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.checkAtLeast(1, args.length);
        
        Function body;
        if (args[0].type() == Types.FUNCTION) {
            body = ((FunctionValue) args[0]).getValue();
        } else {
            body = Functions.get(args[0].asString());
        }
        
        // Сдвигаем аргументы
        final Value[] params = new Value[args.length - 1];
        if (params.length > 0) {
            System.arraycopy(args, 1, params, 0, params.length);
        }

        final Thread thread = new Thread(() -> body.execute(params));
        thread.setUncaughtExceptionHandler(Console::handleException);
        thread.start();
        return NumberValue.ZERO;
    }
}
