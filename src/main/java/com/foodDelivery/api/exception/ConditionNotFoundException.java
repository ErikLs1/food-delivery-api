package com.foodDelivery.api.exception;

/**
 * Exception is thrown when Condition data cannot be found.
 */
public class ConditionNotFoundException extends RuntimeException {

    /**
     * A new ConditionNotFoundException with the specified detailed message.
     *
     * @param message the detail message.
     */
    public ConditionNotFoundException(String message) {
        super(message);
    }
}
