package me.Animemchik.modules.std;

import me.Animemchik.lib.Arguments;
import me.Animemchik.lib.Function;
import me.Animemchik.lib.FunctionValue;
import me.Animemchik.lib.NumberValue;
import me.Animemchik.lib.Value;
import me.Animemchik.lib.ValueUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class std_sync implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(1, args.length);

        final BlockingQueue<Value> queue = new LinkedBlockingQueue<>(2);
        final Function synchronizer = (sArgs) -> {
            try {
                queue.put(sArgs[0]);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return NumberValue.ZERO;
        };
        final Function callback = ValueUtils.consumeFunction(args[0], 0);
        callback.execute(new FunctionValue(synchronizer));

        try {
            return queue.take();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
    }
    
}
