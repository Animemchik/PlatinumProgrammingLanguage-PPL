package me.Animemchik.lib;

import java.util.HashMap;
import java.util.Map;

public final class Functions {
    private final static Map<String, Function> functions;

    static {
        functions = new HashMap<>();
    }

    public static boolean constantExists(String key) {
        return functions.containsKey(key);
    }

    public static Function get(String key) {
        if (!constantExists(key)) throw new RuntimeException("Unknown function " + key);
        return functions.get(key);
    }

    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}