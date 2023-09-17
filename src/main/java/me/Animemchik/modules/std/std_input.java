package me.Animemchik.modules.std;

import me.Animemchik.lib.Function;
import me.Animemchik.lib.StringValue;
import me.Animemchik.lib.Value;
import java.util.Scanner;

public final class std_input implements Function {

    @Override
    public Value execute(Value... args) {
        try (Scanner sc = new Scanner(System.in)) {
            for (Value arg : args) {
                System.out.print(arg.asString());
            }
            return new StringValue(sc.nextLine());
        }
    }
}
