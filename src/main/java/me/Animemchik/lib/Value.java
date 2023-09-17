package me.Animemchik.lib;

public interface Value extends Comparable<Value> {

    Object raw();

    int asInteger();

    double asNumber();

    String asString();

    Types type();
}
