package me.Animemchik.exceptions;

public final class ArgumentsMismatchException extends RuntimeException {

    public ArgumentsMismatchException() {
    }

    public ArgumentsMismatchException(String message) {
        super(message);
    }
}