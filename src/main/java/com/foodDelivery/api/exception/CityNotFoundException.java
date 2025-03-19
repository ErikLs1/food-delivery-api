package com.foodDelivery.api.exception;

/**
 * Exception is thrown when City data cannot be found.
 */
public class CityNotFoundException extends RuntimeException {

    /**
     * A new CityNotFoundException with the specified detailed message.
     * @param message the detail message.
     */
    public CityNotFoundException(String message) {
        super(message);
    }
}
