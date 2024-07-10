package org.example.utils.exception;

public class FileContentException extends Exception {

    public FileContentException(final String parMessage) {
        super(parMessage);
    }

    public FileContentException(final String parMessage, final Throwable parThrowable) {
        super(parMessage, parThrowable);
    }
}
