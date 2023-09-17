package me.Animemchik.parser.ast;

import me.Animemchik.lib.Value;

public interface Expression extends Node {
    Value eval();
}
