package com.foodDelivery.api.exception;

/**
 * Exception is thrown when the provided observation time cannot be parsed.
 */
public class ParseObservationTimeException extends RuntimeException {

    /**
     * A new ParseObservationTimeException with the specified detailed message.
     *
     * @param message the detail message.
     */
    public ParseObservationTimeException(String message) {
        super(message);
    }
}
