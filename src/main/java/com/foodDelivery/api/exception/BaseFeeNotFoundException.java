package com.foodDelivery.api.exception;

/**
 * Exception is thrown when BaseFee data cannot be found.
 */
public class BaseFeeNotFoundException extends RuntimeException {

    /**
     * A new BaseFeeNotFoundException with the specified detailed message.
     *
     * @param message the detail message.
     */
    public BaseFeeNotFoundException(String message) {
        super(message);
    }
}
