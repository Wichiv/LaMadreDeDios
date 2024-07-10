package org.example.utils.exception;

public class UnknownFileException extends Exception {

    public UnknownFileException(final String parMessage, final Throwable parThrowable) {
        super(parMessage, parThrowable);
    }
}
