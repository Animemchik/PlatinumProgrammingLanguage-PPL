package me.Animemchik.exceptions;

public final class ParseException extends RuntimeException {

    public ParseException() {
        super();
    }

    public ParseException(String string) {
        super(string);
    }
}