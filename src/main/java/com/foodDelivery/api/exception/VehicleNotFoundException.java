package com.foodDelivery.api.exception;

/**
 * Exception is thrown when Vehicle data cannot be found.
 */
public class VehicleNotFoundException extends RuntimeException {

    /**
     * A new VehicleNotFoundException with the specified detailed message.
     *
     * @param message The detail message.
     */
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
