package org.mozilla.browserquest.model.exception;

public class DuplicateObjectException extends RuntimeException {

    public DuplicateObjectException() {
    }

    public DuplicateObjectException(String message) {
        super(message);
    }
}
