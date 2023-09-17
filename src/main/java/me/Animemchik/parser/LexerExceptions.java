package me.Animemchik.parser;

public final class LexerExceptions extends RuntimeException {

    public LexerExceptions(String message) {
        super(message);
    }

    public LexerExceptions(String message, int row, int column, String file) {
        super("[" + row + ":" + column+ "] " + message);
    }
}
