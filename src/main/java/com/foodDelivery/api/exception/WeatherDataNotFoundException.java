package com.foodDelivery.api.exception;

/**
 * Exception is thrown when WeatherData data cannot be found.
 */
public class WeatherDataNotFoundException extends RuntimeException {

    /**
     * A new WeatherDataNotFoundException with the specified detailed message.
     *
     * @param message the detail message.
     */
    public WeatherDataNotFoundException(String message) {
        super(message);
    }
}
